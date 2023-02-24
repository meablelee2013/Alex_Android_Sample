package com.oriente.aptsample.startup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;


import com.oriente.aptsample.startup.LogUtils;
import com.oriente.aptsample.startup.startup.AbsTask;
import com.oriente.aptsample.startup.startup.Task;

import java.util.ArrayList;
import java.util.List;

public class Task4 extends Task<Void> {

    static List<Class<? extends AbsTask<?>>> mDepends;

    static {
        mDepends = new ArrayList<>();
        mDepends.add(Task2.class);
    }

    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t + " Task4：学习Http");
        SystemClock.sleep(1_000);
        LogUtils.log(t + " Task4：掌握Http");
        return null;
    }


    @Override
    public List<Class<? extends AbsTask<?>>> dependencies() {
        return mDepends;
    }


}
