package com.oriente.aptsample.startup.manager;

import com.oriente.aptsample.startup.Result;
import com.oriente.aptsample.startup.Startup;

import java.util.concurrent.ConcurrentHashMap;

public class StartupCacheManager {
    //ConcurrentHashMap 要求key value不能为null，所以Value用一个类封装保证永远不为空
    private ConcurrentHashMap<Class<? extends Startup>, Result> mInitializedComponent = new ConcurrentHashMap<Class<? extends Startup>, Result>();
    private static StartupCacheManager mInstance;

    private StartupCacheManager() {

    }

    public static StartupCacheManager getInstance() {
        if (mInstance == null) {
            synchronized (StartupCacheManager.class) {
                if (mInstance == null) {
                    mInstance = new StartupCacheManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * save result of initialized component
     */
    public void saveInitializedComponent(Class<? extends Startup> zClass, Result result) {
        mInitializedComponent.put(zClass, result);
    }

    public boolean hasInitialized(Class<? extends Startup> zClass) {
        return mInitializedComponent.contains(zClass);
    }

    public <T> Result<T> obtainInitializedResult(Class<? extends Startup<T>> zClass) {
        return mInitializedComponent.get(zClass);
    }

    public void remove(Class<? extends Startup> zClass) {
        mInitializedComponent.remove(zClass);
    }

    public void clear() {
        mInitializedComponent.clear();
    }


}
