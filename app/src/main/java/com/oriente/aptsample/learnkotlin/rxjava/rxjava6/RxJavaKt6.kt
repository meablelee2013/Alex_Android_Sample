package com.oriente.aptsample.learnkotlin.rxjava.rxjava6

fun main() {
    create {//没有输入，lambda最后一行作为输出，并作为下一个操作符的输入

    }.map {//有输入 有输出

    }.map {

    }.consumer {//有输入，没有输出，在这里事件就被消费掉了

    }
    run {  }
}

fun <R> create(action: () -> R): R = action()

fun <T, R> T.map(action: T.() -> R): R = action(this)

fun <T> T.consumer(action: T.() -> Unit) = action(this)