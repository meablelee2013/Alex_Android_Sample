
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

public class Task3 extends Task<Void> {

    static List<Class<? extends AbsTask<?>>> mDepends;

    static {
        mDepends = new ArrayList<>();
        mDepends.add(Task1.class);
    }

    @Nullable
    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t+" Task3：学习设计模式");
        SystemClock.sleep(2_000);
        LogUtils.log(t+" Task3：掌握设计模式");
        return null;
    }


    //    执行此任务需要依赖哪些任务
    @Nullable
    @Override
    public List<Class<? extends AbsTask<?>>> dependencies() {
        return mDepends;
    }


}
