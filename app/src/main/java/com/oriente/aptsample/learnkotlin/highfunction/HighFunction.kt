package com.oriente.aptsample.learnkotlin.highfunction

//开味菜
fun aa() {} //就是一个普通的函数
val aa2 = {} //aa2是接受一个匿名函数的变量而已，这个变量，可以执行这个匿名函数
//共同点 他们的结果是一样的

val aa3 = aa2 //属于函数引用的概念
val aa4 = ::aa //::的含义，就是把普通函数变成函数引用，所以可以赋值传递了给另一个变量了


//函数的函数就是高阶函数
//考试：函数返回一个函数

val fun3 = fun(n1: Int, n2: Int)  : String  = "haha"

val fun4 = fun(a: Int, b: Int) //fun4函数本身 //fun(n1: Int, n2: Int)是一个匿名函数
        : (Int, Int) -> String  //返回函数的声明
        = { n1, n2 ->"两个数相加:${n1 + n2}" //返回函数的实现
}

//终极
val k01 :(String) ->(String) ->(Boolean) ->(Int) ->(String)->Int ={
    it:String ->
        {it:String ->
            {it:Boolean ->
                {it:Int ->
                    {it:String ->
                        1000
                    }
                }
            }
        }
}
fun main() {
//    println(fun4(100, 200)(1000, 2000))//100传给了a,200传给了b,1000传给了n1,2000传给了n2,上面的 返回的函数并没有使用a,b两个参数

    println(k01("aaa")("bbb")(true)(199)("aaa"))
}








