package com.oriente.aptsample.learnkotlin.rxjava.customrxjava4



fun main() {

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
class Helper<T>( var item: T)

//有输入有输出，输出通过helper包装，返回helper,这样就能链式调用
private fun <T,R> Helper<T>.map(action: T.() -> R): Helper<R> = Helper(action(item))

//有输入，无输出
private fun <T> Helper<T>.consumer(action: T.() -> Unit) = action(item)
//思路:输入不用考虑，因为最后一行作为流向输出的数据
//思路:中转站就是为了转化(保存)我们的create和map等的数据
private fun <R> create(action: () -> R): Helper<R> = Helper(action())