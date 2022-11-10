package com.oriente.aptsample.startup;

import java.util.concurrent.Executor;

public interface Dispatcher {

    /**
     * 是否在主线程中执行
     */
    boolean callCreateOnMainThread();

    /**
     * 是否需要等待訪任务执行完成
     *
     * @return
     */
    boolean waitOnMainThread();

    /**
     * 等待
     */
    void toWait();

    /**
     * 有父任务执行完成
     * 计算器-1
     */
    void toNotify();

    Executor executor();

    /**
     * 线程优先级
     */
    int getThreadPriority();

}
