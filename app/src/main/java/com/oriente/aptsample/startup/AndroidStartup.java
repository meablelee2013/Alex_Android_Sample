package com.oriente.aptsample.startup;

import com.oriente.aptsample.startup.manager.ExecutorManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public abstract class AndroidStartup<T> implements Startup<T> {

    private CountDownLatch mWaitCountDown = new CountDownLatch(getDependenciesCount());

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return null;
    }

    /**
     * 入度数
     *
     * @return
     */
    @Override
    public int getDependenciesCount() {
        List<Class<? extends Startup<?>>> dependencies = dependencies();
        return dependencies == null ? 0 : dependencies.size();
    }

    @Override
    public Executor executor() {
        return ExecutorManager.ioExecutor;
    }

    @Override
    public void toWait() {
        try {
            mWaitCountDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toNotify() {
        mWaitCountDown.countDown();
    }

    @Override
    public int getThreadPriority() {
        return android.os.Process.THREAD_PRIORITY_DEFAULT;
    }
}
