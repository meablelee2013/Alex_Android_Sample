package com.example.asm

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

import java.util.function.Consumer
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class AsmPlugin extends Transform implements Plugin<Project> {
    private AsmConfigModel config
    private int api = Opcodes.ASM7


    @Override
    void apply(Project project) {
        println('apply asmplugin')
        def android = project.extensions.getByType(AppExtension.class)
        android.registerTransform(this)
        config = project.extensions.create("asConfig", AsmConfigModel.class)

    }

    @Override
    String getName() {
        return "AsmPlugin"
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
        super.transform(transformInvocation)
        if (!transformInvocation.isIncremental()) {
            //不是增量编译删除所有的outputProvider
            transformInvocation.getOutputProvider().deleteAll();
        }
        // 获取输入源
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        inputs.forEach(transformInput -> {
            Collection<DirectoryInput> directoryInputs = transformInput.getDirectoryInputs();
            Collection<JarInput> jarInputs = transformInput.getJarInputs();
            directoryInputs.forEach(new Consumer<DirectoryInput>() {
                @Override
                public void accept(DirectoryInput directoryInput) {
                    try {
                        // 处理输入源
//                        handleDirectoryInput(directoryInput);
                    } catch (IOException e) {
                        System.out.println("handleDirectoryInput error:" + e.toString());
                    }
                }
            });

            for (DirectoryInput directoryInput : directoryInputs) {
                // 获取output目录
                File dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(),
                        directoryInput.getScopes(),
                        Format.DIRECTORY);
                //这里执行字节码的注入，不操作字节码的话也要将输入路径拷贝到输出路径
                try {
                    FileUtils.copyDirectory(directoryInput.getFile(), dest);
                } catch (IOException e) {
                    System.out.println("output copy error:" + e.toString());
                }
            }

            for (JarInput jarInput : jarInputs) {
                // 获取output目录
                File dest = transformInvocation.getOutputProvider().getContentLocation(jarInput.getName(),
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR);
                //这里执行字节码的注入，不操作字节码的话也要将输入路径拷贝到输出路径
                try {
                    FileUtils.copyFile(jarInput.getFile(), dest);
                } catch (IOException e) {
                    System.out.println("output copy error:" + e.toString());
                }
            }
        });
    }


}