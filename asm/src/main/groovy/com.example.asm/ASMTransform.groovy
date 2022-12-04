package com.example.asm

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.google.common.collect.FluentIterable
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class ASMTransform extends Transform {

    Project project

    ASMTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "ASMTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);

        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider()
        //清理文件
        outputProvider.deleteAll()


        //得到所有的输入
        Collection<TransformInput> inputs = transformInvocation.getInputs()
        for (TransformInput input : inputs) {
            // 处理class目录
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                // 直接复制输出到对应的目录
                String dirName = directoryInput.getName()
                File src = directoryInput.getFile()
                System.out.println("目录：" + src)
                String md5Name = DigestUtils.md5Hex(src.getAbsolutePath())
                File dest = outputProvider.getContentLocation(dirName + md5Name, directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY)
                //todo 插桩
                processInject(src, dest)
            }
            // 处理jar（依赖）的class todo 先不处理了
            for (JarInput jarInput : input.getJarInputs()) {
                String jarName = jarInput.getName()
                File src = jarInput.getFile()
                System.out.println("jar包：" + src)
                String md5Name = DigestUtils.md5Hex(src.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                File dest = outputProvider.getContentLocation(jarName + md5Name, jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR)
                FileUtils.copyFile(src, dest)
            }
        }
    }

    private void processInject(File src, File dest) throws IOException {
        String dir = src.getAbsolutePath()
        FluentIterable<File> allFiles = FileUtils.getAllFiles(src)

        for (File file : allFiles) {
//            boolean needAsm = needAsm(file);
//            System.out.println("processInject" + "---fileName is " + file.getName() + "---needAsm(file)=" + needAsm);
//            if (needAsm) {
            FileInputStream fis = new FileInputStream(file)
            //分析器
            ClassReader cr = new ClassReader(fis)
            // 写出器
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES)
            //分析，处理结果写入cw  //插桩
            cr.accept(new ASMClassVisitor(cw, file.getName()), ClassReader.EXPAND_FRAMES)

            byte[] newClassBytes = cw.toByteArray()
            String absolutePath = file.getAbsolutePath()
            String fullClassPath = absolutePath.replace(dir, "")
            File outFile = new File(dest, fullClassPath)
            FileUtils.mkdirs(outFile.getParentFile())
            FileOutputStream fos = new FileOutputStream(outFile)
            fos.write(newClassBytes)
            fos.close()
//            }
        }

    }
}