package com.oriente.aptsample.learnkotlin.inline


//const val PI=3.1415
//class InlineTest2 {
//    fun test(){
//        val a = PI
//    }
//}

/********************************************************/

//fun hello(postAction:() ->Unit){
//    println("hello")
//    postAction()
//}

//调用处代码
//fun main(){
//    hello {
//        println("bye")
//    }
//}

//实际编译的代码（大致） 内部会创建一个post的对象，如果在for里面就会大量创建对象
//fun main(){
//    val post = object :Function0<Unit>{
//        override fun invoke() {
//            println("bye")
//        }
//    }
//    hello(post)
//}
/********************************************************/
//如果使用inline来修饰
//inline fun hello(postAction:() ->Unit){
//    println("hello")
//    postAction()
//}

//调用处代码
//fun main(){
//    hello {
//        println("bye")
//    }
//}

//编译后的代码如下
//fun main(){
//    println("hello")
//    println("bye")
//}
//使用inline，可以避免了函数类型的参数所造成的临时对象的创建了

/********************************************************/
//inline fun hello(preAction:()->Unit,postAction:()->Unit){
//    preAction()
//    println("hello")
//    postAction()
//}
//
//fun main() {
//    hello({
//        println("I am pre action")
//    },{
//        println("I am post action")
//    })
//}

//添加了inline之后 实际编译的代码如下
//fun main(){
//    println("I am pre action")
//    println("hello")
//    println("I am post action")
//}

/********************************************************/
