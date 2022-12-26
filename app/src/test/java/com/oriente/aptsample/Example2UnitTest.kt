package com.oriente.aptsample

import okhttp3.internal.Util.threadFactory
import org.junit.Test
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class Example2UnitTest {
    @Test
    fun test() {
        println("aaa")
    }

    @Test
    fun testThreadPoolExecutor() {
       var executorServiceOrNull = ThreadPoolExecutor(0, Int.MAX_VALUE, 60, TimeUnit.SECONDS,
            SynchronousQueue(), threadFactory("okhttp Dispatcher", false))


        executorServiceOrNull.execute {
            println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
            println("1")
            Thread.sleep(10000)
            println("1 执行完成")
        }
        executorServiceOrNull.execute {
            println("2")
        }
        executorServiceOrNull.execute {
            println("3")
        }

        while (true){

        }
    }
}