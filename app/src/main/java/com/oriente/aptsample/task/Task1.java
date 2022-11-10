package com.oriente.aptsample.task;

import android.content.Context;
import android.os.SystemClock;

import com.oriente.aptsample.AndroidStartup;
import com.oriente.aptsample.Startup;

import java.util.List;

public class Task1 extends AndroidStartup<String> {

    @Override
    public String create(Context context) {
        System.out.println("Task1 学习Java基础");
        SystemClock.sleep(3000);
        System.out.println("Task1 掌握Java基础");
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
}
