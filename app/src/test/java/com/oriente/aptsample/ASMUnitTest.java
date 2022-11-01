package com.oriente.aptsample;


import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ASMUnitTest {

    @Test
    public void test() throws IOException {
        //获取待插桩的字节码
        System.out.println("ASMUnitTest.test");
        FileInputStream fis = new FileInputStream("/Users/lizhigang/myproject/Alex_Android_Sample/app/src/test/java/com/oriente/aptsample/InjectTest.class");

        //执行插桩

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassReader classReader = new ClassReader(fis);
        classReader.accept(new ClassVisitor(Opcodes.ASM5, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                System.out.println(name);
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MyMethodVisitor(Opcodes.ASM5, methodVisitor, access, name, descriptor);
            }
        }, classReader.EXPAND_FRAMES);

        //输出结果 首先要创建这个文件夹  /Users/lizhigang/myproject/Alex_Android_Sample/app/src/test/java2/
        byte[] bytes = classWriter.toByteArray();
        FileOutputStream fos = new FileOutputStream("/Users/lizhigang/myproject/Alex_Android_Sample/app/src/test/java2/com/oriente/aptsample/InjectTest.class");
        fos.write(bytes);
        fos.close();
    }

    class MyMethodVisitor extends AdviceAdapter {
        int firstIndex, secondIndex;

        /**
         * Constructs a new {@link AdviceAdapter}.
         *
         * @param api           the ASM API version implemented by this visitor. Must be one of {@link
         *                      Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
         * @param methodVisitor the method visitor to which this adapter delegates calls.
         * @param access        the method's access flags (see {@link Opcodes}).
         * @param name          the method's name.
         * @param descriptor    the method's descriptor (see {@link Type Type}).
         */
        protected MyMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            //java 代码
            // long l = System.currentTimeMillis();
            //字节码代码
            // invokestatic   Method java/lang/System.currentTimeMillis:()J
            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
            // lstore_1
            //创建一个本地变量，
            firstIndex = newLocal(Type.LONG_TYPE);
            storeLocal(firstIndex, Type.LONG_TYPE);
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            //invokestatic   Method java/lang/System.currentTimeMillis:()J
            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
            //创建一个本地变量，
            //lstore_3
            secondIndex = newLocal(Type.LONG_TYPE);
            storeLocal(secondIndex, Type.LONG_TYPE);


            // getstatic     Field java/lang/System.out:Ljava/io/PrintStream;
            getStatic(Type.getType("Ljava/lang/System;"), "out", Type.getType("Ljava/io/PrintStream;"));
            //new    class java/lang/StringBuilder
            newInstance(Type.getType("Ljava/lang/StringBuilder;"));
            //dup
            dup();
            // invokespecial  Method java/lang/StringBuilder."<init>":()V
            invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"), new Method("<init>", "()V"));
            //ldc           String execute:
            visitLdcInsn("execute");
            // invokevirtual Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
            // lload_3
            loadLocal(secondIndex);
            // lload_1
            loadLocal(firstIndex);
            // lsub
            math(SUB, Type.LONG_TYPE);
            // invokevirtual  Method java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(J)Ljava/lang/StringBuilder;"));
            //ldc         String ms
            visitLdcInsn("ms");
            //invokevirtual  Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("append","(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
            //invokevirtual  Method java/lang/StringBuilder.toString:()Ljava/lang/String;
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("toString","()Ljava/lang/String;"));
            //invokevirtual    Method java/io/PrintStream.println:(Ljava/lang/String;)V
            invokeVirtual(Type.getType("Ljava/io/PrintStream;"),new Method("println","(Ljava/lang/String;)V;"));


        }

        //同步代码块的进入
        @Override
        public void monitorEnter() {
            super.monitorEnter();

        }

        //同步代码块的退出
        @Override
        public void monitorExit() {
            super.monitorExit();
        }
    }
}
