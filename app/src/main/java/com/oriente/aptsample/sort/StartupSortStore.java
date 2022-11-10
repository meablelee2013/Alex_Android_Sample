package com.oriente.aptsample.sort;

import com.oriente.aptsample.Startup;

import java.util.List;
import java.util.Map;

public class StartupSortStore {

    List<Startup<?>> result;
    Map<Class<? extends Startup>, Startup<?>> startupMap;
    //任务依赖表
    Map<Class<? extends Startup>, List<Class<? extends Startup>>> startupDependenceChildrenMap;

    public StartupSortStore(List<Startup<?>> result, Map<Class<? extends Startup>, Startup<?>> startupMap, Map<Class<? extends Startup>, List<Class<? extends Startup>>> startupDependenceChildrenMap) {
        this.result = result;
        this.startupMap = startupMap;
        this.startupDependenceChildrenMap = startupDependenceChildrenMap;
    }

    public List<Startup<?>> getResult() {
        return result;
    }
}
