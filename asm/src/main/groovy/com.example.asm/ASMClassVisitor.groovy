package com.example.asm

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class ASMClassVisitor extends ClassVisitor {

    /**
     * 得到类名*/
    private String className;

    ASMClassVisitor(ClassVisitor cv, String fileName) {
        super(Opcodes.ASM7, cv)
        println "ASMClassVisitor fileName is ${fileName}"
        className = fileName.substring(0, fileName.lastIndexOf("."))
        println "ASMClassVisitor className is ${className}"
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new ASMMethodAdviceVisitor(mv, access, name, descriptor, className);
    }
}