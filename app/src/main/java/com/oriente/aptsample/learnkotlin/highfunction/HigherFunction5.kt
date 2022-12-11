package com.oriente.aptsample.learnkotlin.highfunction

fun showAction(action:(Int) ->String){
    println(action(88))
}

fun lambdaImpl(value:Int):String ="我的值是$value"

//:: 把这个函数变成函数引用，
val r1:Function1<Int,String> =::lambdaImpl
var r2:(Int) ->String = r1
var r3:Int.() ->String =r2 //Int.()==(Int) Int.()属于来源，编译器会自动把来源作为第一个参数

fun main() {
    //:: 把函数变成函数引用，就可以赋值给变量了
//    showAction (::lambdaImpl)
//    showAction {
//        "我的值是 $it"
//    }
    showAction (r1)
    showAction (r2)
    showAction (r3)
}