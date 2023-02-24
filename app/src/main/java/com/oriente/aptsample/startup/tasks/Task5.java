package com.oriente.aptsample.startup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;


import com.oriente.aptsample.startup.LogUtils;
import com.oriente.aptsample.startup.startup.AbsTask;
import com.oriente.aptsample.startup.startup.Task;

import java.util.ArrayList;
import java.util.List;

public class Task5 extends Task<Void> {

    static List<Class<? extends AbsTask<?>>> mDepends;

    static {
        mDepends = new ArrayList<>();
        mDepends.add(Task3.class);
        mDepends.add(Task4.class);
    }

    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper() ? "主线程: " : "子线程: ";
        LogUtils.log(t + " Task5：学习OkHttp");
        SystemClock.sleep(500);
        LogUtils.log(t + " Task5：掌握OkHttp");
        return null;
    }

    //执行此任务需要依赖哪些任务
    @Override
    public List<Class<? extends AbsTask<?>>> dependencies() {
        return mDepends;
    }
}
