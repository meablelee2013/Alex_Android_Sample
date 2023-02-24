package com.oriente.aptsample.startup.startup.sort;



import com.oriente.aptsample.startup.startup.AbsTask;
import com.oriente.aptsample.startup.startup.TaskSortStore;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopologySort {

    public static TaskSortStore sort(List<? extends AbsTask<?>> taskList) {
        Map<Class<? extends AbsTask>, Integer> inDegreeMap = new HashMap<>();
        Deque<Class<? extends AbsTask>> zeroDeque = new ArrayDeque<>();

        Map<Class<? extends AbsTask>, AbsTask<?>> taskMap = new HashMap<>();
        Map<Class<? extends AbsTask>, List<Class<? extends AbsTask>>> taskChildrenMap = new HashMap<>();

        for (AbsTask<?> task : taskList) {
            taskMap.put(task.getClass(), task);
            //记录每个任务的入度数（依赖的任务数）
            int dependenciesCount = task.getDependenciesCount();
            inDegreeMap.put(task.getClass(), dependenciesCount);
            //记录入度数（依赖的任务数）为0的任务
            if (dependenciesCount == 0) {
                zeroDeque.offer(task.getClass());
            } else {
                //遍历本任务的依赖（父）任务列表
                for (Class<? extends AbsTask<?>> parent : task.dependencies()) {
                    List<Class<? extends AbsTask>> children = taskChildrenMap.get(parent);
                    if (children == null) {
                        children = new ArrayList<>();
                        //记录这个父任务的所有子任务
                        taskChildrenMap.put(parent, children);
                    }
                    children.add(task.getClass());
                }
            }
        }
        List<AbsTask<?>> result = new ArrayList<>();
        List<AbsTask<?>> main = new ArrayList<>();
        List<AbsTask<?>> threads = new ArrayList<>();
        //处理入度为0的任务
        while (!zeroDeque.isEmpty()) {
            Class<? extends AbsTask> cls = zeroDeque.poll();
            AbsTask<?> task = taskMap.get(cls);
            if (task.isMustRunOnMainThread()) {
                main.add(task);
            } else {
                threads.add(task);
            }

            //删除此入度为0的任务
            if (taskChildrenMap.containsKey(cls)) {
                List<Class<? extends AbsTask>> childStartup = taskChildrenMap.get(cls);
                for (Class<? extends AbsTask> childCls : childStartup) {
                    Integer num = inDegreeMap.get(childCls);
                    inDegreeMap.put(childCls, num - 1);
                    if (num - 1 == 0) {
                        zeroDeque.offer(childCls);
                    }
                }
            }
        }
        result.addAll(threads);
        result.addAll(main);
        return new TaskSortStore(result, taskMap, taskChildrenMap);
    }
}
