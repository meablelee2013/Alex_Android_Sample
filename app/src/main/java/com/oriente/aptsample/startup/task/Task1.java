package com.oriente.aptsample.startup.task;

import android.content.Context;
import android.util.Log;

import com.oriente.aptsample.startup.AndroidStartup;
import com.oriente.aptsample.startup.Startup;

import java.util.List;

public class Task1 extends AndroidStartup<String> {

    @Override
    public String create(Context context) {
        super.create(context);
        Log.d("alex", threadResult + "---Task1 学习Java基础");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("alex", threadResult + "---Task1 掌握Java基础");
        return "Task1 返回数据";
    }

    /**
     * 执行此任务需要依赖哪些任务
     *
     * @return
     */
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return super.dependencies();
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
