package com.example.asm

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter

class ASMMethodAdviceVisitor extends AdviceAdapter {

    private String className
    private String methodName
    private boolean inject
    private int index
    private int start, end

    ASMMethodAdviceVisitor(MethodVisitor mv, int access, String name, String desc, String className) {
        super(Opcodes.ASM7, mv, access, name, desc);
        this.methodName = name
        this.className = className
    }

    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        System.out.println("visitAnnotation " + "---className is " + className + "---methodName is " + methodName + "---" + System.currentTimeMillis());
        if ("Lcom/example/annotation/TimeCheck;" == descriptor) {
            inject = true
        }
        return super.visitAnnotation(descriptor, visible)
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()
        System.out.println("onMethodEnter " + "---className is " + className + "---methodName is " + methodName + "---" + System.currentTimeMillis());
        if (inject) {
            /**
             * sourceCode
             * long var2 = System.currentTimeMillis();
             *
             * asm code
             *      Label label0 = new Label();
             *      methodVisitor.visitLabel(label0);
             *      methodVisitor.visitLineNumber(11, label0);
             *      methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
             *      methodVisitor.visitVarInsn(LSTORE, 2);*/
            // 0: invokestatic #2 // Method java/lang/System.currentTimeMillis:()J
            // 3: lstore_1
            //储备本地变量备用
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            index = newLocal(Type.LONG_TYPE);
            start = index;
            mv.visitVarInsn(LSTORE, start);
        }
    }

    /**
     * source code
     *     long var4 = System.currentTimeMillis();
     *     System.out.println("==========>MainActivity execute onCreate: " + (var4 - var2) + "ms.");
     *
     * asmCode---通过AsmPlugin生成的
     *     Label label4 = new Label();
     *     methodVisitor.visitLabel(label4);
     *     methodVisitor.visitLineNumber(15, label4);
     *     methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
     *     methodVisitor.visitVarInsn(LSTORE, 4);
     *
     *     Label label5 = new Label();
     *     methodVisitor.visitLabel(label5);
     *     methodVisitor.visitLineNumber(16, label5);
     *
     *     methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
     *
     *     methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
     *     methodVisitor.visitInsn(DUP);
     *     methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
     *     methodVisitor.visitLdcInsn("==========>MainActivity execute onCreate: ");
     *     methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
     *     methodVisitor.visitVarInsn(LLOAD, 4);
     *     methodVisitor.visitVarInsn(LLOAD, 2);
     *     methodVisitor.visitInsn(LSUB);
     *     methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
     *     methodVisitor.visitLdcInsn("ms.");
     *     methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
     *     methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
     *     methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);*/

    @Override
    protected void onMethodExit(int opcode) {
        System.out.println("onMethodExit " + "---className is " + className + "---methodName is " + methodName + "---" + System.currentTimeMillis());
        if (inject) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            index = newLocal(Type.LONG_TYPE);
            end = index;
            mv.visitVarInsn(LSTORE, end);

            // getstatic #3 // Field java/lang/System.out:Ljava/io/PrintStream;
            //获得静态成员 out
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

            // new #4 // class java/lang/StringBuilder
            // 引入类型 分配内存 并dup压入栈顶让下面的INVOKESPECIAL 知道执行谁的构造方法
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);

            //invokevirtual #7   // Method java/lang/StringBuilder.append:
            // (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 执行构造方法
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);

            // ldc #6 // String execute:
            // 把常量压入栈顶 后面使用
            mv.visitLdcInsn("==========>" + className + " execute " + methodName + ": ");

            //invokevirtual #7 // Method java/lang/StringBuilder.append: (Ljava/lang/String;)
            // Ljava/lang/StringBuilder;
            // 执行append方法，使用栈顶的值作为参数
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

            // lload_3 获得存储的本地变量
            // lload_1
            // lsub   减法指令
            mv.visitVarInsn(LLOAD, end);
            mv.visitVarInsn(LLOAD, start);
            mv.visitInsn(LSUB);

            // invokevirtual #8 // Method java/lang/StringBuilder.append:(J)
            // Ljava/lang/StringBuilder;
            // 把减法结果append
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);

            //append "ms."
            mv.visitLdcInsn("ms.");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

            //tostring
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);

            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
    }
}