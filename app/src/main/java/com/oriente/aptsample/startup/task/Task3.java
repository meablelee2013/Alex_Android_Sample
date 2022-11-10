package com.oriente.aptsample.startup.task;

import android.content.Context;
import android.util.Log;

import com.oriente.aptsample.startup.AndroidStartup;
import com.oriente.aptsample.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task3 extends AndroidStartup<Void> {

    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(Task1.class);
    }

    @Override
    public Void create(Context context) {
        super.create(context);
        Log.d("alex", threadResult + "---Task3 学习设计模式");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("alex", threadResult + "---Task3 掌握设计模式");
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
        return true;
    }
}
