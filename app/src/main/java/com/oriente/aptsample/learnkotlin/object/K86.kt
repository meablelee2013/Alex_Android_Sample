package com.oriente.aptsample.learnkotlin.`object`

/**
 * public final class K86 {
    @NotNull
    public static final K86 INSTANCE;

    public final void show() {
        String var1 = "我是show函数";
        System.out.println(var1);
    }

    private K86() {
    }

    static {
        K86 var0 = new K86();
        INSTANCE = var0;
    }
}
 */
//object K86 {
//
//    fun show() = println("我是show函数")
//}

class K87{
    companion object{
        val name ="67890"
        fun show() = println("afdsfaasf")
    }
    object AA{
        val name ="67890"
        fun show() = println("afdsfaasf")
    }
}

fun main() {

}


/**
 * 伴生对象
 * 1：首先会创建一个静态内部类
 * 2：静态内部类的构造方法私有，外部不能使用
 * 3：静态成员变量，把静态内部类new出来赋值给这个变量
 */

/**
 * public final class K87 {
    @NotNull
    private static final String name = "67890";
    @NotNull
    public static final K87.Companion Companion = new K87.Companion((DefaultConstructorMarker)null);


    public static final class AA {
        @NotNull
        private static final String name;
        @NotNull
        public static final K87.AA INSTANCE;

        @NotNull
        public final String getName() {
         return name;
        }

        public final void show() {
            String var1 = "afdsfaasf";
            System.out.println(var1);
        }

        private AA() {
        }

        static {
            K87.AA var0 = new K87.AA();
            INSTANCE = var0;
            name = "67890";
        }
    }


    public static final class Companion {
    @NotNull
    public final String getName() {
     return K87.name;
    }

    public final void show() {
        String var1 = "afdsfaasf";
        System.out.println(var1);
    }

    private Companion() {
    }

    // $FF: synthetic method
    public Companion(DefaultConstructorMarker $constructor_marker) {
     this();
    }
    }
    }
 */