package com.oriente.aptsample.startup.startup.provider;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.text.TextUtils;


import com.oriente.aptsample.startup.startup.AbsTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskInitializer {
    public static String META_VALUE = "android.startup";

    public static List<AbsTask<?>> discoverAndInitializ(Context context,
                                                        String providerName) throws Exception {
        Map<Class<? extends AbsTask>, AbsTask<?>> startups = new HashMap<>();
        //获得manifest contentProvider中的meta-data
        ComponentName provider = new ComponentName(context, providerName);
        ProviderInfo providerInfo = context.getPackageManager().getProviderInfo(provider,
                PackageManager.GET_META_DATA);
        //得到manifest中配置的任务
        for (String key : providerInfo.metaData.keySet()) {
            String value = providerInfo.metaData.getString(key);
            if (TextUtils.equals(META_VALUE, value)) {
                Class<?> clazz = Class.forName(key);
                if (AbsTask.class.isAssignableFrom(clazz)) {
                    doInitialize((AbsTask<?>) clazz.newInstance(), startups);
                }
            }
        }

        List<AbsTask<?>> result = new ArrayList<>(startups.values());
        return result;
    }

    private static void doInitialize(AbsTask<?> startup,
                                     Map<Class<? extends AbsTask>, AbsTask<?>> startups) throws Exception {
        //避免重复 不能使用List
        startups.put(startup.getClass(), startup);
        if (startup.getDependenciesCount() != 0) {
            //遍历父任务
            for (Class<? extends AbsTask<?>> dependency : startup.dependencies()) {
                doInitialize(dependency.newInstance(), startups);
            }
        }
    }


}
