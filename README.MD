### 原始的类
```
public class InjectTest {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(100);
    }
}
```

### 想要通过插桩生成的最终类
```
public class InjectTest {

    public static void main(String[] args) throws InterruptedException {
        long l = System.currentTimeMillis();
        Thread.sleep(100);
        long e = System.currentTimeMillis();
        System.out.println("execute: " + (e - l) + "ms");
    }
}
```

### 1 首先添加依赖，为了方便快速查看检测结果，我们在test 的unittest目录下进行，所以添加的依赖如下
```
testImplementation 'org.ow2.asm:asm:7.1'
testImplementation 'org.ow2.asm:asm-commons:7.1'
```
先把包导入进来之后，我们先把我们最终的类如下
```
public class InjectTest {

    public static void main(String[] args) throws InterruptedException {
        long l = System.currentTimeMillis();
        Thread.sleep(100);
        long e = System.currentTimeMillis();
        System.out.println("execute: " + (e - l) + "ms");
    }
}

```
最终的类的class文件拿到，所以我们执行如下命令,也就是通过javac 把.java文件编译成.class文件

```
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.oriente.aptsample;

public class InjectTest {
    public InjectTest() {
    }

    public static void main(String[] var0) throws InterruptedException {
        Thread.sleep(100L);
    }
}
```

### 2 然后合建ASMUnitTest.java文件，如下
```
public class ASMUnitTest {

    @Test
    public void test() throws IOException {
        //获取待插桩的字节码
        System.out.println("ASMUnitTest.test");
        FileInputStream fis = new FileInputStream("/Users/lizhigang/myproject/Alex_Android_Sample/app/src/test/java/com/oriente/aptsample/InjectTest.class");

        
    }

    
}
```
主要是分三步，分别是获取待插桩的字节码，执行插桩，输出结果 
#### 获取待插桩的字节码
```
 //获取待插桩的字节码
        System.out.println("ASMUnitTest.test");
        FileInputStream fis = new FileInputStream("/Users/lizhigang/myproject/Alex_Android_Sample/app/src/test/java/com/oriente/aptsample/InjectTest.class");

```

#### 执行插桩
```
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
```

其中自定了方法visitor 类
```
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
```

#### 输出结果
```
  //输出结果 首先要创建这个文件夹  /Users/lizhigang/myproject/Alex_Android_Sample/app/src/test/java2/
        byte[] bytes = classWriter.toByteArray();
        FileOutputStream fos = new FileOutputStream("/Users/lizhigang/myproject/Alex_Android_Sample/app/src/test/java2/com/oriente/aptsample/InjectTest.class");
        fos.write(bytes);
        fos.close();
```

所以最终的类如下
```
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
```

最终运行test 的unit方法就能看到结果