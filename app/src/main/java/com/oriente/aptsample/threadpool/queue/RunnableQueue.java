package com.oriente.aptsample.threadpool.queue;

public interface RunnableQueue {
    void offer(Runnable runnable);

    Runnable poll();

    int size();
}
