package com.oriente.aptsample;

import android.app.Application;

import com.oriente.aptsample.startup.manager.StartupManager;
import com.oriente.aptsample.startup.task.Task1;
import com.oriente.aptsample.startup.task.Task2;
import com.oriente.aptsample.startup.task.Task3;
import com.oriente.aptsample.startup.task.Task4;
import com.oriente.aptsample.startup.task.Task5;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new StartupManager.Builder().addStartup(new Task5())
                .addStartup(new Task4()).addStartup(new Task3()).addStartup(new Task2()).addStartup(new Task1()).build(this).start().await();

    }
}
