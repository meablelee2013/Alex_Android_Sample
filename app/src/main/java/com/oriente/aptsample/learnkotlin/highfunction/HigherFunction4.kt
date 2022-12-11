package com.oriente.aptsample.learnkotlin.highfunction


//给泛型增加具名abc扩展函数
fun <T> T.abc() {
    //this==T 本身 ==调用者本身
}

//我给泛型增加匿名扩展函数
fun <T> T.myRunOk(lambda: T.() -> Boolean) = lambda()

fun <T> T.myRunOk1(n1: T, lambda: T.(T) -> Boolean) = lambda(n1)

fun <T> T.myRunOk2(lambda: (T) -> Boolean) = lambda(this)

fun main() {
//    "Alex".myRunOk {
//        //this==T 本身 ==调用者本身 ==“this”
//        println("我是 $this")
//        true
//    }

//    "hello".myRunOk1("alex") {
//        println("this ==$this it ==$it")
//        true
//    }

//    "alex".run1 {
//        this.length
//        length
//    }
//    "alex".run2 {
//        it.length
//    }
    "aaa".run {
        println("this $this")
    }
    "aaa".apply {

    }

}

//在lambda里，如果参数是这样的 T.()和（T）的区别
//T.() {持有this == T本身}
//(T) {持有it==T本身}

fun <T> T.run1(lambda:T.() ->Unit)=this.lambda()
fun <T> T.run2(lambda:(T) ->Unit)=lambda(this)



/**
 * @kotlin.internal.InlineOnly
    public inline fun <T> T.apply(block: T.() -> Unit): T {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }
        block()
        return this
    }

 我们来解析上面的高阶函数
 1：函数名称---首先对泛型扩展apply 具名函数
 2：:T 是返回值，也就是返回跟调用者相同的类型
 2:参数是一个lambda表达式 T.() -> Unit 没有参数，返回值类型是Unit T.()表示这个lambda的实现部分持有this,而this就是T本身

 */

