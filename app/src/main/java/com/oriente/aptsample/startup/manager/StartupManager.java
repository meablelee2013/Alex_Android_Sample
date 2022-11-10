package com.oriente.aptsample.startup.manager;

import android.content.Context;
import android.os.Looper;

import com.oriente.aptsample.startup.AndroidStartup;
import com.oriente.aptsample.startup.Startup;
import com.oriente.aptsample.startup.run.StartupRunnable;
import com.oriente.aptsample.startup.sort.StartupSortStore;
import com.oriente.aptsample.startup.sort.TopologySort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class StartupManager {

    private Context context;
    private List<AndroidStartup<?>> startupList;
    private StartupSortStore startupSortStore;

    private CountDownLatch awaitCountDownLatch = new CountDownLatch(5);

    public StartupManager(Context context, List<AndroidStartup<?>> startupList) {
        this.context = context;
        this.startupList = startupList;
    }

    public StartupManager start() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("请在主线程中调用");
        }
        startupSortStore = TopologySort.sort(startupList);
        for (Startup<?> startup : startupSortStore.getResult()) {
            StartupRunnable startupRunnable = new StartupRunnable(context, startup, this);
            if (startup.callCreateOnMainThread()) {
                startupRunnable.run();
            } else {
                startup.executor().execute(startupRunnable);
            }
        }
        return this;
    }

    public void await() {
        try {
            awaitCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notifyChildren(Startup<?> startup) {
        //获取已经完成的当前任务的所有子任务
        if (!startup.callCreateOnMainThread() && startup.waitOnMainThread()) {
            awaitCountDownLatch.countDown();
        }
        if (startupSortStore.getStartupDependenceChildrenMap().containsKey(startup.getClass())) {
            List<Class<? extends Startup>> childStartupCls = startupSortStore.getStartupDependenceChildrenMap().get(startup.getClass());
            for (Class<? extends Startup> cls : childStartupCls) {
                Startup<?> childStartup = startupSortStore.getStartupMap().get(cls);
                //父任务做完任务之后，需要通知所有依赖他的子任务可以执行任务了
                childStartup.toNotify();
            }
        }
    }

    public static class Builder {
        private List<AndroidStartup<?>> startupList = new ArrayList<>();

        public Builder addStartup(AndroidStartup<?> startup) {
            startupList.add(startup);
            return this;
        }

        public Builder addAllStartup(List<AndroidStartup<?>> startups) {
            startupList.addAll(startups);
            return this;
        }

        public StartupManager build(Context context) {
            return new StartupManager(context, startupList);
        }
    }
}
