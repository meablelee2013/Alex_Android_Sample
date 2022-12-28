package com.alex.startup

import java.util.concurrent.Executor

interface Dispatcher {

    /**
     * 是否在主线程创建任务
     */
    fun callCreateOnMainThread()

    /**
     * 是否要等待该任务执行完成
     */
    fun waitOnMainThread(): Boolean

    /**
     * 利用CountDownLatch等待
     */
    fun toWait()

    fun toNotify()

    fun executor(): Executor

    /**
     * 线程优先级
     */
    fun getThreadPriority(): Int
}