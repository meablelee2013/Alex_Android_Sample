package com.oriente.aptsample.startup.startup.run;

import android.content.Context;
import android.os.Process;

import com.oriente.aptsample.startup.startup.AbsTask;
import com.oriente.aptsample.startup.startup.Result;
import com.oriente.aptsample.startup.startup.manage.TaskManager;
import com.oriente.aptsample.startup.startup.manage.TaskResultStoreManager;


public class TaskRunnable implements Runnable {
    private TaskManager mTaskManager;
    private AbsTask<?> mTask;
    private Context mContext;

    public TaskRunnable(Context context, AbsTask<?> task, TaskManager taskManager) {
        this.mContext = context;
        this.mTask = task;
        this.mTaskManager = taskManager;
    }

    @Override
    public void run() {
        Process.setThreadPriority(mTask.getThreadPriority());
        mTask.toWait();
        Object result = mTask.create(mContext);
        TaskResultStoreManager.getInstance().saveTaskResult(mTask.getClass(), new Result(result));
        mTaskManager.notifyChildren(mTask);

    }
}
