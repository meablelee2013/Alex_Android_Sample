package com.oriente.aptsample.startup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.oriente.aptsample.startup.LogUtils;
import com.oriente.aptsample.startup.startup.AbsTask;
import com.oriente.aptsample.startup.startup.Task;

import java.util.ArrayList;
import java.util.List;

public class Task2 extends Task<Void> {

    static List<Class<? extends AbsTask<?>>> mDepends;

    static {
        mDepends = new ArrayList<>();
        mDepends.add(Task1.class);
    }

    @Nullable
    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper() ? "主线程: " : "子线程: ";
        LogUtils.log(t + " Task2：学习Socket");
        SystemClock.sleep(800);
        LogUtils.log(t + " Task2：掌握Socket");
        return null;
    }

    @Override
    public List<Class<? extends AbsTask<?>>> dependencies() {
        return mDepends;
    }
}
