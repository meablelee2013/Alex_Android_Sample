package com.alex.startup

import com.alex.startup.manager.ExecutorManager
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor

abstract class AndroidStartup<T> : Startup<T> {

    var mWaitCountDown: CountDownLatch = CountDownLatch(getDependenciesCount())

    override fun dependencies(): List<Class<out Startup<*>?>?>? {
        return null
    }

    override fun getDependenciesCount(): Int {
        val dependencies = dependencies()
        return dependencies?.count() ?: 0
    }

    override fun executor(): Executor {
        return ExecutorManager.mainExecutor
    }

    override fun toWait() {
        try {
            mWaitCountDown.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun toNotify() {
        mWaitCountDown.countDown()
    }

    override fun getThreadPriority(): Int {
        return android.os.Process.THREAD_PRIORITY_DEFAULT
    }
}