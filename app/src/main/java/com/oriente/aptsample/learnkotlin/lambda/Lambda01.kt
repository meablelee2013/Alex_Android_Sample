package com.oriente.aptsample.learnkotlin.lambda

fun main() {

//    val method05 = { number1: Int, number2: Int -> number1 + number2 }
//    println(method05(1, 3))

    //先声明，后实现
//    val method06: (Int) -> String = fun(value: Int): String = value.toString()
//    println(method06(6))

    //用先声明，后实现，来自动推断  输入和输出都是自动推断出来的
//    val method07: (Int) -> String = fun(value) = value.toString()
//    println(method07(999))

    //声明+实现 一起实现   =左边是声明，=右边是实现
//    val method08: (Int) -> String = { value -> value.toString() }
//    println(method08(888))

//    val method09: (String, String) -> Unit = { str1, str2 ->
//        println((str1 + str2))
//    }
//    method09("aaa", "bbb")

//    val method10: (String) -> Unit = {
//        /**只有一个参数，如果不写，就会有默认的it参数**/
//        println("你传进来的参数是 $it")
//    }
//    method10("I love kotlin")


//    val method11: (Int) -> Unit = {
//        when (it) {
//            1 -> println("你传进来 的是一")
//            in 2..60 -> println("你传递进来 的值 是2到60之间的值")
//            else -> println("都不满足")
//        }
//    }
//    method11(60)


//    val method12: (Int) -> String = {
//        when (it) {
//            1 -> "你传进来 的是一"
//            in 2..60 -> "你传递进来 的值 是2到60之间的值"
//            else -> "都不满足"
//        }
//    }
//    println(method12(60))

    // _ 表示拒收 可以提高一点性能
//    val method13: (Int, Int) -> Unit = { _, number2 ->
//        println("number2 $number2")
//    }
//    method13(13,100)

    //输入和输出是同一个类型
//    val method14 = { str: Any -> str }
//    println(method14(true))

//    val method15 = { sex: Char ->
//        println(if (sex == '男') "你是男生" else if (sex == '女') "你是女生" else "未知")
//    }
//    method15('男')
//    method15.invoke('男')


    //(Int) ->Unit
//    var method16 = { number: Int ->
//        println("你输入的值是$number")
//    }
//    //覆盖method16,虽然你覆盖了，但类型仍然是(Int) ->Unit
//    method16 = {
//        println("覆盖的也能拿到原来的值$it")
//    }
//    method16(9999)

    //给String 增加匿名的扩展函数
//    val method19: String.() -> Unit = {
//        println("你就是$this") //this就是调用者本身
//    }
//    "DDD".method19()


//    val method20: Int.(Int) -> String = {
//        "两数相加的结果是:${this + it}"
//    }
//    //下面两个方法等价
//    println(1.method20(100))
//    println(method20(1,100))

    /**
     * --------------------前面是一直在操作输入-----------------------------
     */
//    fun t01() {
//        println("1111")
//    }
//
//    fun t02() {
//        6789
//    }
//
//    fun t03() {
//        true
//    }
//
//    fun t04(): String {
//        return "Alex"
//    }


//    fun s01() = {} //（） ->Unit 函数返回一个函数
    //方法后面添加=，表示一个方法返回一个方法
//    fun s02() = { /** 函数返回函数**/
//        println("Ok")
//    }
//    s02()()

    //    fun s03() = run {  //返回Boolean
//        true
//    }
    //要看run里面的返回的什么类型
    fun s03() :String= run {  //run 返回{}里面的函数返回类型
        "A"
    }
    fun s04() = { true } //() ->Boolean  函数返回一个函数
    println(s03())
    println(s04()())

}

fun String.shows() {
    println("你是$this")
}



