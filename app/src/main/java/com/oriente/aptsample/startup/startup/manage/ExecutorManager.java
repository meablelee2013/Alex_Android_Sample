package com.oriente.aptsample.startup.startup.manage;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorManager {

    public static ThreadPoolExecutor sCpuExecutor;
    public static ExecutorService sIoExecutor;
    public static Executor sMainExecutor;

    //获得CPU核心数
    private static int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 5));
    private static int MAX_POOL_SIZE = CORE_POOL_SIZE;
    private static long KEEP_ALIVE_TIME = 5L;

    static {
        sCpuExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(),
                Executors.defaultThreadFactory());
        sCpuExecutor.allowCoreThreadTimeOut(true);
        sIoExecutor = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
        sMainExecutor = new Executor() {
            Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }
}
