package com.oriente.aptsample.startup.startup;


import android.os.Process;


import com.oriente.aptsample.startup.startup.manage.ExecutorManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public abstract class Task<T> implements AbsTask<T> {
    private CountDownLatch mWaitCountDown = new CountDownLatch(getDependenciesCount());

    /**
     * 此Task能否执行，取决于dependencies中有没有其他的Task，
     * 如果有，则需要先让dependencies里的task先执行
     *
     * @return
     */
    @Override
    public List<Class<? extends AbsTask<?>>> dependencies() {
        return null;
    }

    @Override
    public int getDependenciesCount() {
        List<Class<? extends AbsTask<?>>> dependencies = dependencies();
        return dependencies == null ? 0 : dependencies.size();
    }


    @Override
    public Executor executor() {
        return ExecutorManager.sIoExecutor;
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
        return Process.THREAD_PRIORITY_DEFAULT;
    }


    @Override
    public boolean isMustRunOnMainThread() {
        return false;
    }

    @Override
    public boolean waitOnMainThread() {
        return false;
    }
}
