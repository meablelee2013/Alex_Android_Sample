package com.oriente.aptsample.startup;

import android.app.Application;

import com.oriente.aptsample.startup.startup.manage.TaskManager;
import com.oriente.aptsample.startup.tasks.Task1;
import com.oriente.aptsample.startup.tasks.Task2;
import com.oriente.aptsample.startup.tasks.Task3;
import com.oriente.aptsample.startup.tasks.Task4;
import com.oriente.aptsample.startup.tasks.Task5;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        new Task1().create(MyApplication.this);
//        new Task2().create(MyApplication.this);
//        new Task3().create(MyApplication.this);
//        new Task4().create(MyApplication.this);
//        new Task5().create(MyApplication.this);
//
//        new TaskManager.Builder()
//                .addTask(new Task5())
//                .addTask(new Task4())
//                .addTask(new Task3())
//                .addTask(new Task2())
//                .addTask(new Task1())
//                .build(this)
//                .start().await();
    }
}
