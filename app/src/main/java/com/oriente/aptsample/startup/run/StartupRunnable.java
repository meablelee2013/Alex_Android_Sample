package com.oriente.aptsample.startup.run;

import android.content.Context;

import com.oriente.aptsample.startup.Result;
import com.oriente.aptsample.startup.Startup;
import com.oriente.aptsample.startup.manager.StartupCacheManager;
import com.oriente.aptsample.startup.manager.StartupManager;

public class StartupRunnable implements Runnable {

    private StartupManager startupManager;
    private Startup<?> startup;
    private Context context;

    public StartupRunnable(Context context, Startup<?> startup, StartupManager startupManager) {
        this.startupManager = startupManager;
        this.startup = startup;
        this.context = context;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(startup.getThreadPriority());
        startup.toWait();
        Object result = startup.create(context);
        StartupCacheManager.getInstance().saveInitializedComponent(startup.getClass(), new Result(result));
        startupManager.notifyChildren(startup);
    }
}
