package com.oriente.aptsample.learnkotlin.inline


inline fun hello(){
    println("hello")
}

fun main(){
    hello()
}
//实际编译后的代码如下
/**
 * 以下是decompile之后的代码
 * public final class InlineTestKt {
        public static final void hello() {
            int $i$f$hello = 0;
            String var1 = "hello";
            System.out.println(var1);
         }

        public static final void main() {
            int $i$f$hello = false;
            String var1 = "hello";
            System.out.println(var1);
        }

        // $FF: synthetic method
        public static void main(String[] var0) {
             main();
        }
    }
 */
/**
 * 原来的调用栈
 * println()
 * hello()
 * main()
 *
 * 使用inline之后调用栈如下
 * println()
 * main()
 */

