package com.oriente.aptsample.startup.startup.manage;


import com.oriente.aptsample.startup.startup.AbsTask;
import com.oriente.aptsample.startup.startup.Result;

import java.util.concurrent.ConcurrentHashMap;

public class TaskResultStoreManager {
    private ConcurrentHashMap<Class<? extends AbsTask>, Result> mTaskResultMap =
            new ConcurrentHashMap();
    private static TaskResultStoreManager mInstance;

    private TaskResultStoreManager() {

    }

    public static TaskResultStoreManager getInstance() {
        if (mInstance == null) {
            synchronized (TaskResultStoreManager.class) {
                if (mInstance == null) {
                    mInstance = new TaskResultStoreManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * save task result to Map.
     */
    public void saveTaskResult(Class<? extends AbsTask> zClass, Result result) {
        mTaskResultMap.put(zClass, result);
    }

    /**
     * check initialized.
     */
    public boolean hasResult(Class<? extends AbsTask> zClass) {
        return mTaskResultMap.containsKey(zClass);
    }

    public <T> Result<T> getTaskResult(Class<? extends AbsTask<T>> zClass) {
        return mTaskResultMap.get(zClass);
    }


    public void remove(Class<? extends AbsTask> zClass) {
        mTaskResultMap.remove(zClass);
    }

    public void clear() {
        mTaskResultMap.clear();
    }
}
