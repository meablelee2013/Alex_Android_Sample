package com.example.asm

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.Opcodes

import java.util.function.Consumer
import java.util.jar.JarEntry
import java.util.jar.JarFile

class AsmPlugin extends Transform implements Plugin<Project> {
    private AsmConfigModel config
    private int api = Opcodes.ASM7
    Project project;

    @Override
    void apply(Project project) {
        this.project = project;
        println('apply asmplugin-----------------------------------')
        //首先拿到app这个插件
        def android = project.extensions.getByType(AppExtension)

        android.registerTransform(this)
        config = project.extensions.create("asConfig", AsmConfigModel.class)

    }

    @Override
    String getName() {
        return "AsmPlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        //接受的输入类型是class
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        //扫描范围是全工程
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        //支持增量编译 添加这个之后，除了第一次，后面的编译都能加速
        return true
    }


    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        project.getLogger().warn("asm plugin transform----------")
        if (!transformInvocation.isIncremental()) {
            //不是增量编译删除所有的outputProvider
            transformInvocation.getOutputProvider().deleteAll()
        }
        // 获取输入源
        Collection<TransformInput> inputs = transformInvocation.getInputs()
        inputs.forEach(transformInput -> {

            //文件夹的输入
            Collection<DirectoryInput> directoryInputs = transformInput.getDirectoryInputs()

            directoryInputs.forEach(new Consumer<DirectoryInput>() {
                @Override
                void accept(DirectoryInput directoryInput) {
                    scanDirectory(directoryInput.file)
                }
            })
            //jar的输入
            Collection<JarInput> jarInputs = transformInput.getJarInputs()

            jarInputs.forEach(new Consumer<JarInput>() {
                @Override
                void accept(JarInput jarInput) {
                    scanJar(jarInput.file)
                }
            })
        })
    }

    private scanDirectory(File file) {
        file.eachFileRecurse {
            if (it.isFile()) {
                project.logger.warn("file---" + it.absolutePath)
            }
        }
    }

    private void scanJar(File file) {
        //通过jar包的file对象获取一个jar包对象
        def jarFile = new JarFile(file)
        try {
            def enumeration = jarFile.entries()
            //通过遍历获取jar包里所有的class然后打印出来，
            while (enumeration.hasMoreElements()) {
                def jarEntry = enumeration.nextElement()
                def entryName = jarEntry.name
                if (entryName.startsWith("androidx/") || entryName.startsWith("android/")) {
                    break
                }
                project.logger.info("jarName is " + file.absolutePath + "---classPath---" + entryName)
            }
        } finally {
            if (jarFile != null) {
                //关闭jar包占用，我们重新上传，但是这个时候jar包已经被占用了
                jarFile.close()
            }
        }
    }
}