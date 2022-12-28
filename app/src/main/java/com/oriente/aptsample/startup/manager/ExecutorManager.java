package com.oriente.aptsample.startup.manager;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 参照android 8.0的AsyncTask源码
 * private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
 * // We want at least 2 threads and at most 4 threads in the core pool,
 * // preferring to have 1 less than the CPU count to avoid saturating
 * // the CPU with background work
 * private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
 * private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
 * private static final int KEEP_ALIVE_SECONDS = 30;
 * <p>
 * private static final ThreadFactory sThreadFactory = new ThreadFactory() {
 * private final AtomicInteger mCount = new AtomicInteger(1);
 * <p>
 * public Thread newThread(Runnable r) {
 * return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
 * }
 * };
 * <p>
 * private static final BlockingQueue<Runnable> sPoolWorkQueue =
 * new LinkedBlockingQueue<Runnable>(128);
 * <p>
 * /**
 * * An {@link Executor} that can be used to execute tasks in parallel.
 * <p>
 * public static final Executor THREAD_POOL_EXECUTOR;
 * *
 * static {
 * ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(
 * CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE_SECONDS,TimeUnit.SECONDS,
 * sPoolWorkQueue,sThreadFactory);
 * threadPoolExecutor.allowCoreThreadTimeOut(true);
 * THREAD_POOL_EXECUTOR=threadPoolExecutor;
 * }
 * <p>
 * <p>
 * <p>
 * 线程池如何设计
 * cpu密集型 --- 计算密集弄 线程数小 Ncpu(count)
 * io密集型  --- 网络 文件  2* Ncpu(count)+1
 **/


public class ExecutorManager {

    public static ThreadPoolExecutor cpuExecutor;
    public static ExecutorService ioExecutor;
    public static Executor mainExecutor;

    //获取cpu核心数
    private static int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 5));
    private static int MAX_POOL_SIZE = CORE_POOL_SIZE;
    private static long KEEP_ALIVE_TIME = 5L;

    //newCachedThreadPool 这个线程池有什么特点 coreSize=0,maxSize=Integer.MAX_VALUE，且是无界队列
    static {
        cpuExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), new StartupThreadFactory());
        cpuExecutor.allowCoreThreadTimeOut(true);
        ioExecutor = Executors.newCachedThreadPool(new StartupThreadFactory());
        mainExecutor = new Executor() {
            Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }


}
