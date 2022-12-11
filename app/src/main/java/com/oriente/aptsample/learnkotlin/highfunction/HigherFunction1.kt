package com.oriente.aptsample.learnkotlin.highfunction

//高阶函数 入门 ，函数中有lambda就属于高阶函数，函数的函数就是高阶函数




/**接口调用**/


fun main() {
//    var r: String = show01(100) {
//        /**接口实现**/
//        "it 就是${it}"
//    }
    //三数相乘  lambda是最后一个参数，就可以放在外面
    show02(1, 2, 3) { n1, n2, n3 ->
        println(n1 * n2 * n3)
    }
    //三数相加
    show02(1, 2, 3) { n1, n2, n3 ->
        println(n1 + n2 + n3)
    }
    //三数相除
    show02(100, 2, 2) { n1, n2, n3 ->
        println(n1 / n2 / n3)
    }
}

//show01返回类型是String
fun show01(
    number: Int, lambda: (Int) -> String
    /**lambda: (Int) -> String 类似于接口声明**/
) = lambda.invoke(number)


//需求：三数相乘，三数相加，三数相除 由用户指定，我只定义规则（高阶函数）
fun show02(number1: Int, number2: Int, number3: Int, lambda: (Int, Int, Int) -> Unit) =
    lambda(number1, number2, number3) //调用lambda