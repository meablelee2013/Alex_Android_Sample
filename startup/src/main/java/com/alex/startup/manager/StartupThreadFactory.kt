package com.alex.startup.manager

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * 自定义线程的生产，这样可以给线程带上名称，方便后面排查问题
 */
class StartupThreadFactory : ThreadFactory {
    private val poolNumber = AtomicInteger(1)
    private val namePrefix: String = "startup-" + poolNumber.getAndIncrement() + "-thread-"


    override fun newThread(r: Runnable?): Thread {
        return Thread(
            Thread.currentThread().threadGroup, r, namePrefix + poolNumber.getAndIncrement(), 0
        )
    }
}