package com.oriente.aptsample.startup.sort;

import com.oriente.aptsample.startup.Startup;

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

    public Map<Class<? extends Startup>, Startup<?>> getStartupMap() {
        return startupMap;
    }

    public Map<Class<? extends Startup>, List<Class<? extends Startup>>> getStartupDependenceChildrenMap() {
        return startupDependenceChildrenMap;
    }
}
