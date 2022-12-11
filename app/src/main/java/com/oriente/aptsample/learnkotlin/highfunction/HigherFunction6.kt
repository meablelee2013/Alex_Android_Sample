package com.oriente.aptsample.learnkotlin.highfunction


class HigherFunction6<T> {
    //定义集合 元素类型为Lambda 输入泛型
    private val actoions = arrayListOf<(T?) -> Unit>() //每个元素都是事件，(T?) -> Unit

    //定义集合2 元素类型为泛型?
    private val valeues = arrayListOf<T?>() //每个元素都是数据，T?

    fun addListener(value: T?, action: (T?) -> Unit) {
        actoions += action
        valeues += value
    }

    fun touchListener() {
        if (actoions.isEmpty()) {
            println("你还没有添加任何事件哦")
            return
        }
        actoions.forEachIndexed { index: Int, action: (T?) -> Unit ->
            action(valeues[index])//执行所有元素==lambda规则==用户自定义事件体
        }
    }
}

fun main() {

    val func = HigherFunction6<String>()
    //模拟点击
    func.touchListener()


    //方式1 参数为匿名函数，且是写死的
    func.addListener("alex1") {
        println("事件被触发 执行了 的值为$it")
    }
    //模拟点击
    func.touchListener()


    //方式2 参数为具名函数
    func.addListener("alex2", ::show)
    //模拟点击
    func.touchListener()

    //只有函数本身使用::能变成函数引用，这个引用可以赋值给变量了
    val result: (Any?) -> Unit = ::show
    //方式3
    func.addListener("alex3", result)
    //模拟点击
    func.touchListener()


    val result2: (Any?) -> Unit = {
        println("result2 事件被触发 执行了 的值为$it")
    }
    //方式4
    func.addListener("alex4", result2)
    //模拟点击
    func.touchListener()

    // lambda不支持泛型，用Any代替，
    //lambda不支持默认参数


}

// (T?) -> Unit
fun <T> show(value: T?): Unit {
    println("show  事件被触发 执行了 的值为$value")
}