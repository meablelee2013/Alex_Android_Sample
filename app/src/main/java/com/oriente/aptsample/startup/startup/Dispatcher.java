package com.oriente.aptsample.startup.startup;

import java.util.concurrent.Executor;

public interface Dispatcher {
    /**
     * 是否在主线程执行
     */
    boolean isMustRunOnMainThread();

    /**
     * 是否需要等待该任务执行完成才进到下一个步骤
     */
    boolean waitOnMainThread();

    /**
     * 等待
     */
    void toWait();

    /**
     * 有父任务执行完毕
     * 计数器-1
     */
    void toNotify();

    Executor executor();

    int getThreadPriority();
}
