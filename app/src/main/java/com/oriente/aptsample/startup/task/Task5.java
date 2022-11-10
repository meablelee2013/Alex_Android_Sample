package com.oriente.aptsample.startup.task;

import android.content.Context;
import android.os.SystemClock;

import com.oriente.aptsample.startup.AndroidStartup;
import com.oriente.aptsample.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task5 extends AndroidStartup<Void> {

    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(Task3.class);
        depends.add(Task4.class);
    }

    @Override
    public Void create(Context context) {
        System.out.println("Task5 学习OKHttp");
        SystemClock.sleep(3000);
        System.out.println("Task5 掌握OKHttp");
        return null;
    }

    /**
     * 执行此任务需要依赖哪些任务
     *
     * @return
     */
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }

    @Override
    public boolean callCreateOnMainThread() {
        return false;
    }

    @Override
    public boolean waitOnMainThread() {
        return false;
    }
}
