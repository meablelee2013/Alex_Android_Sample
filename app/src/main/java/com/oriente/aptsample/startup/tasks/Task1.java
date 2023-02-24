package com.oriente.aptsample.startup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import androidx.annotation.Nullable;


import com.oriente.aptsample.startup.LogUtils;
import com.oriente.aptsample.startup.startup.AbsTask;
import com.oriente.aptsample.startup.startup.Task;

import java.util.List;

public class Task1 extends Task<String> {

    @Nullable
    @Override
    public String create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper() ? "主线程: " : "子线程: ";
        LogUtils.log(t + " Task1：学习Java基础");
        SystemClock.sleep(3_000);
        LogUtils.log(t + " Task1：掌握Java基础");
        return "Task1返回数据";
    }


    //执行此任务需要依赖哪些任务
    @Override
    public List<Class<? extends AbsTask<?>>> dependencies() {
        return super.dependencies();
    }

}
