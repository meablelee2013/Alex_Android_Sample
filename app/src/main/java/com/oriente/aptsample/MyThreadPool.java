package com.oriente.aptsample;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool {
    //线程池的大小
    int mPoolSize;
    //存放线程的list
    LinkedList<MyThread> threads;
    //正在运行的线程数
    int mWorkingThreadSize;
    //存放任务的队列
    LinkedBlockingQueue<Runnable> taskQueue;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    public static final int DEFAULT_POLL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;

    final ReentrantLock mainLock = new ReentrantLock();

    public MyThreadPool() {
        this(DEFAULT_POLL_SIZE);
    }

    public MyThreadPool(int mPoolSize) {
        this.mPoolSize = mPoolSize;
        threads = new LinkedList<>();
        taskQueue = new LinkedBlockingQueue<>(mPoolSize * 4);
        mWorkingThreadSize = 0;
    }


    public void execute(Runnable runnable) {
        mainLock.lock();
        try {
            //工作线程数小于线程池的大小时，创建线程
            if (mWorkingThreadSize < mPoolSize) {
                MyThread myThread = new MyThread(runnable);
                myThread.start();
                threads.add(myThread);
                mWorkingThreadSize++;

            }//线程池已满,将任务放入任务队列中，等待有空闲线程来执行
            else {
                //队列已满，无法添加时，拒绝任务
                if (!taskQueue.offer(runnable)) {
                    rejectTask();
                }
            }

        } finally {
            mainLock.unlock();
        }

    }

    private void rejectTask() {
        System.out.println("任务队列已满，无法继续添加，请扩大您的初始化线程池！");
    }

    class MyThread extends Thread {

        private Runnable taskRunnable;

        public MyThread(Runnable runnable) {
            this.taskRunnable = runnable;
        }

        @Override
        public void run() {
            super.run();
            //该线程一直执行着，不断的从任务队列中取任务执行
            while (true) {
                if (taskRunnable != null) {
                    taskRunnable.run();
                    taskRunnable = null;
                } else {
                    try {
                        Runnable task = taskQueue.take();//会阻塞
                        if (task != null) {
                            task.run();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
