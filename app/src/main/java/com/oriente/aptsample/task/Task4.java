package com.oriente.aptsample.task;

import android.content.Context;
import android.os.SystemClock;

import com.oriente.aptsample.AndroidStartup;
import com.oriente.aptsample.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task4 extends AndroidStartup<Void> {

    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(Task2.class);
    }

    @Override
    public Void create(Context context) {
        System.out.println("Task4 学习Socket");
        SystemClock.sleep(3000);
        System.out.println("Task4 掌握Socket");
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
}
