package com.oriente.aptsample.learnkotlin.rxjava.customrxjava5


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

private fun <R> create(action: () -> R): R = action()

//有输入有输出，输出通过helper包装，返回helper,这样就能链式调用
private fun <T, R> T.map(action: T.() -> R): R = action(this)

//有输入，无输出
private fun <T> T.consumer(action: T.() -> Unit) = action(this)
