package com.oriente.aptsample.startup.sort;

import com.oriente.aptsample.startup.Startup;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopologySort {

    /**
     * 拓扑排序
     */
    public static StartupSortStore sort(List<? extends Startup<?>> startupList) {
        //入度数表
        Map<Class<? extends Startup>, Integer> inDegreeMap = new HashMap<>();
        //0入度表
        Deque<Class<? extends Startup>> zeroDeque = new ArrayDeque<>();

        Map<Class<? extends Startup>, Startup<?>> startupMap = new HashMap<>();
        //任务依赖表
        Map<Class<? extends Startup>, List<Class<? extends Startup>>> startupDependenceChildrenMap = new HashMap<>();

        /**
         * 找出图中0入度的顶点
         */
        for (Startup<?> startup : startupList) {
            startupMap.put(startup.getClass(), startup);

            //记录每个任务的入度数（依赖的任务数）
            int dependenciesCount = startup.getDependenciesCount();
            inDegreeMap.put(startup.getClass(), dependenciesCount);
            if (dependenciesCount == 0) {
                //找出0入度数的节点
                zeroDeque.offer(startup.getClass());
            } else {
                //遍历本任务的依赖（父）任务列表
                for (Class<? extends Startup<?>> parent : startup.dependencies()) {
                    List<Class<? extends Startup>> children = startupDependenceChildrenMap.get(parent);
                    if (children == null) {
                        children = new ArrayList<>();
                        //记录这个父任务的所有子任务
                        startupDependenceChildrenMap.put(parent, children);
                    }
                    children.add(startup.getClass());
                }
            }

        }
        /**
         * 2.1 依次在图中删除这些顶点
         */
        List<Startup<?>> result = new ArrayList<>();
        while (!zeroDeque.isEmpty()) {
            Class<? extends Startup> cls = zeroDeque.poll();
            Startup<?> startup = startupMap.get(cls);
            result.add(startup);
            /**
             * 2.2 删除后再找出现在0入度的顶点
             */
            if (startupDependenceChildrenMap.containsKey(cls)) {
                List<Class<? extends Startup>> childStartup = startupDependenceChildrenMap.get(cls);
                for (Class<? extends Startup> childCls : childStartup) {
                    Integer num = inDegreeMap.get(childCls);
                    inDegreeMap.put(childCls, num - 1);
                    if (num - 1 == 0) {
                        zeroDeque.offer(childCls);
                    }
                }
            }
        }
        return new StartupSortStore(result, startupMap, startupDependenceChildrenMap);
    }
}
