package com.oriente.aptsample.learnkotlin.extentions


/**
 * ****************************************************************************************
 *
 * 1:扩展函数的定义
 * 2:泛型扩展
 * 3:泛型属性
 * 4:为伴生对象添加扩展
 * 5:kotlin常用的扩展
 *  let
 *  run
 *  apply
 *
 * ****************************************************************************************
 */


class Jump {
    fun test() {

    }
}

/**
 *在反编译字节码后，会在Jump里添加这个方法，其中一个关键就是方法参数里需要传一个Jump
 *
 * 并且类开也从Jump改成JumpKt了，这样在java中就可以通过JumpKt.doubleJump()调用了
 *
 *
 * @NotNull
 *   public static final String doubleJump(@NotNull Jump $this$doubleJump) {
 *      Intrinsics.checkNotNullParameter($this$doubleJump, "$this$doubleJump");
 *   return "doubleJump";
}
 */
//扩展函数的定义
fun Jump.doubleJump(): String {
    return "doubleJump"
}

fun main() {
//    println(Jump().doubleJump())

    //泛型扩展
//    val mutableListOf = mutableListOf("Android", "Ios", "Mobile")
//    mutableListOf.swap(0, 1)
//    println(mutableListOf)
    //扩展属性
//    val android = "android"
//    val lastChar = android.lastChar
//    println("lastChar $lastChar")

    //为伴生对象添加扩展
//    Jumper.print("aaaaa")


    //let扩展
//    testLet(null)

    //run扩展
//    testRun(Room("衡山路", 12.0f, 23.9f))

    //apply扩展
    testApply()
}

//泛型扩展
//不管是方法参数还是返回值有泛型，则需要在方法名前加<T>
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    if (index1 < size && index2 < size) {
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }
}

//扩展属性
val String.lastChar: Char get() = this[length - 1]


//为伴生对象添加扩展
class Jumper {
    companion object {}
}

fun Jumper.Companion.print(str: String) {
    println(str)
}

//let扩展
fun testLet(str: String?) {
    //限定作用域
    str.let {
        val str2 = "let 扩展"
        println(it + str2)
    }
    //报错，在let作用域内定义的变量，只能在let范围内使用
    //println(str2)


    //避免为null的操作
    str?.let {
        println(str.length)
    }
}


data class Room(val address: String, var price: Float, var size: Float)

//run 扩展  返回值为最后一行的值或者指定的return 表达式 在run中可以访问对象的仅有属性和方法
fun testRun(room: Room) {
    val run = room.run {
        println("Room: $address $price,$size")
        true
    }
    println(run)
}

//apply扩展 在作用域内可以调用该对象的任意方法，并返回该对象
fun testApply() {
    ArrayList<String>().apply {
        add("testApply")
        add("testApply2")
    }.let { println(it) }
}

/**
 * ************************************************************************************
 * run 和 apply的区别
 * run是以闭包的形式返回最后一行代码的值，而apply是直接返回传入对象本身
 *
 * ************************************************************************************
 */












