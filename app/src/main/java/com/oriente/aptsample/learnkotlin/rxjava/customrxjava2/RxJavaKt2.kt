package com.oriente.aptsample.learnkotlin.rxjava.customrxjava2



fun main() {
    //需求
    /** 类似RxJava的操作符
     *
     * create{ 没有输入，没有it,也没有this
     *  最后一行作为输出，当成下一个环节的输入
     * }.map{
     *  最后一行作为输出，当成下一个环节的输入
     * }.map{
     *  最后一行作为输出，当成下一个环节的输入
     * }.map{
     *  最后一行作为输出，当成下一个环节的输入
     * }.consumer{
     *
     * }
     */
    create {
        "alex is a big man"
    }.map {
        this.length
    }.map {
        "内容长度为$this"
    }.map {
        "【$this】"
    }.consumer {
        println("消费此次操作 $this")
    }


}

/**
 * 中转站的作用
 * 1:保存最新的item作为下一个操作符的输入
 * 2:链式调用
 */
class Helper<T>(private var item: T) {
    //有输入有输出，输出通过helper包装，返回helper,这样就能链式调用
    fun <R> map(action: T.() -> R): Helper<R> {
        val newItem: R = action(item)
        return Helper<R>(newItem)
    }

    //有输入，无输出
    fun consumer(action: T.() -> Unit) = action(item)
}

//思路:输入不用考虑，因为最后一行作为流向输出的数据
//思路:中转站就是为了转化(保存)我们的create和map等的数据
private fun  <R> create(action: () -> R): Helper<R> {
    val r: R = action()
    return Helper<R>(r)
}