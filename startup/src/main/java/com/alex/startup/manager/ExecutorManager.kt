package com.alex.startup.manager

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*

object ExecutorManager {

    var cpuExecutor: ThreadPoolExecutor
    var ioExecutor: ExecutorService
    var mainExecutor: Executor

    //获取cpu核心数
    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    private val CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 5))
    private val MAX_POOL_SIZE = CORE_POOL_SIZE
    private const val KEEP_ALIVE_TIME = 5L
    private var handler = Handler(Looper.getMainLooper())

    //newCachedThreadPool 这个线程池有什么特点 coreSize=0,maxSize=Integer.MAX_VALUE，且是无界队列
    init {
        cpuExecutor = ThreadPoolExecutor(
            CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, LinkedBlockingDeque<Runnable>(), StartupThreadFactory()
        )
        cpuExecutor!!.allowCoreThreadTimeOut(true)
        ioExecutor = Executors.newCachedThreadPool(StartupThreadFactory())

        mainExecutor = Executor {
            handler.post {
                it
            }
        }

    }
}