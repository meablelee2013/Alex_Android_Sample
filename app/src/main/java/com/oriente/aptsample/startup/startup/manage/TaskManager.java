package com.oriente.aptsample.startup.startup.manage;

import android.content.Context;
import android.os.Looper;


import com.oriente.aptsample.startup.LogUtils;
import com.oriente.aptsample.startup.startup.AbsTask;
import com.oriente.aptsample.startup.startup.TaskSortStore;
import com.oriente.aptsample.startup.startup.run.TaskRunnable;
import com.oriente.aptsample.startup.startup.sort.TopologySort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskManager {


    private CountDownLatch mAwaitCountDownLatch;
    private Context mContext;
    private List<AbsTask<?>> mTaskList;
    private TaskSortStore mTaskSortStore;

    public TaskManager(Context context, List<AbsTask<?>> taskList, CountDownLatch awaitCountDownLatch) {
        this.mContext = context;
        this.mTaskList = taskList;
        this.mAwaitCountDownLatch = awaitCountDownLatch;
    }

    public TaskManager start() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("请在主线程调用！");
        }
        mTaskSortStore = TopologySort.sort(mTaskList);
        for (AbsTask<?> task : mTaskSortStore.getResult()) {
            TaskRunnable taskRunnable = new TaskRunnable(mContext, task, this);
            if (task.isMustRunOnMainThread()) {//主线程
                taskRunnable.run();
            } else {
                task.executor().execute(taskRunnable);//子线种
            }
        }
        return this;
    }

    public void await() {
        try {
            mAwaitCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notifyChildren(AbsTask<?> task) {
        if (task.isMustRunOnMainThread() && task.waitOnMainThread()) {
            mAwaitCountDownLatch.countDown();
        }
        //获得已经完成的当前任务的所有子任务
        if (mTaskSortStore.getTaskChildrenMap().containsKey(task.getClass())) {
            List<Class<? extends AbsTask>> childTaskCls = mTaskSortStore.getTaskChildrenMap().get(task.getClass());
            for (Class<? extends AbsTask> cls : childTaskCls) {
                //通知子任务 startup父任务已完成
                AbsTask<?> childTask = mTaskSortStore.getTaskMap().get(cls);
                childTask.toNotify();
            }
        }
    }


    public static class Builder {
        private List<AbsTask<?>> taskList = new ArrayList<>();

        public Builder addTask(AbsTask<?> startup) {
            taskList.add(startup);
            return this;
        }

        public Builder addAllTask(List<AbsTask<?>> tasks) {
            taskList.addAll(tasks);
            return this;
        }

        public TaskManager build(Context context) {
            AtomicInteger needAwaitCount = new AtomicInteger();
            for (AbsTask<?> task : taskList) {
                //记录需要主线程等待完成的异步任务
                if (task.isMustRunOnMainThread() && task.waitOnMainThread()) {
                    needAwaitCount.incrementAndGet();
                    LogUtils.log("needAwaitTask=" + task.getClass().getSimpleName());
                }
            }
            LogUtils.log("needAwaitCount=" + needAwaitCount.get());
            CountDownLatch awaitCountDownLatch = new CountDownLatch(needAwaitCount.get());
            return new TaskManager(context, taskList, awaitCountDownLatch);
        }
    }
}
