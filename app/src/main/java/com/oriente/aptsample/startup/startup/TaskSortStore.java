package com.oriente.aptsample.startup.startup;

import java.util.List;
import java.util.Map;

public class TaskSortStore {
    //所有的任务
    List<AbsTask<?>> result;
    Map<Class<? extends AbsTask>, AbsTask<?>> taskMap;
    //当前任务的子任务
    Map<Class<? extends AbsTask>, List<Class<? extends AbsTask>>> taskChildrenMap;

    public TaskSortStore(List<AbsTask<?>> result, Map<Class<? extends AbsTask>, AbsTask<?>> taskMap, Map<Class<? extends AbsTask>, List<Class<? extends AbsTask>>> taskChildrenMap) {
        this.result = result;
        this.taskMap = taskMap;
        this.taskChildrenMap = taskChildrenMap;
    }

    public List<AbsTask<?>> getResult() {
        return result;
    }

    public void setResult(List<AbsTask<?>> result) {
        this.result = result;
    }

    public Map<Class<? extends AbsTask>, AbsTask<?>> getTaskMap() {
        return taskMap;
    }

    public void setStartupMap(Map<Class<? extends AbsTask>, AbsTask<?>> taskMap) {
        this.taskMap = taskMap;
    }

    public Map<Class<? extends AbsTask>, List<Class<? extends AbsTask>>> getTaskChildrenMap() {
        return taskChildrenMap;
    }

    public void setTaskChildrenMap(Map<Class<? extends AbsTask>, List<Class<? extends AbsTask>>> taskChildrenMap) {
        this.taskChildrenMap = taskChildrenMap;
    }
}
