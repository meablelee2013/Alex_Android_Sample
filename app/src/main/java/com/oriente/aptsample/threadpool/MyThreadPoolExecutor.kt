package com.oriente.aptsample.threadpool

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * 自定义线程池 待完善
 */
class MyThreadPoolExecutor {
    //任务的消息队列
    private var mWorkBlockingQueue: LinkedBlockingQueue<Runnable>

    //cpu核心数
    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    private val CORE_POOL_SIZE = 2.coerceAtLeast((CPU_COUNT - 1).coerceAtMost(4))
    private val MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1
    private val KEEP_ALIVE_SECONDS = 30
    private var mThreadFactory: ThreadFactory

    //核心线程数
    private var mCorePoolSize: Int = CORE_POOL_SIZE

    //最大线程数
    private var mMaxPoolSize: Int = MAXIMUM_POOL_SIZE

    //13
    val COUNT_BITS: Int = (Int.SIZE_BITS shr 1) - 3

    //00011111 11111111 线程的容量
    private val CAPACITY = (1 shl COUNT_BITS) - 1

    // runState is stored in the high-order bits
    //11100000 00000000
    private val RUNNING = -1 shl COUNT_BITS

    //00000000 00000000
    private val SHUTDOWN = 0 shl COUNT_BITS

    //00100000 00000000
    private val STOP = 1 shl COUNT_BITS

    //01000000 00000000
    private val TIDYING = 2 shl COUNT_BITS

    //01100000 00000000
    private val TERMINATED = 3 shl COUNT_BITS

    interface DenyPolicy {
        fun deny(runnable: Runnable, executor: MyThreadPoolExecutor)
    }

    //自定义线程工厂，是方便出现线程相关问题是及时定位(通过name)
    private val sDefaultThreadFactory: ThreadFactory = object : ThreadFactory {
        private val mCount = AtomicInteger(1)
        override fun newThread(r: Runnable): Thread {
            return Thread(r, "MyThreadPoolExecutor #" + mCount.getAndIncrement())
        }
    }

    constructor() {
        mWorkBlockingQueue = LinkedBlockingQueue()
        mThreadFactory = sDefaultThreadFactory
    }

    constructor(corePoolSize: Int, maxPoolSize: Int, workBlockingQueue: LinkedBlockingQueue<Runnable>, threadFactory: ThreadFactory) {
        this.mCorePoolSize = corePoolSize
        this.mMaxPoolSize = maxPoolSize
        this.mWorkBlockingQueue = workBlockingQueue
        this.mThreadFactory = threadFactory
    }

    fun execute(runnable: Runnable) {

    }

}