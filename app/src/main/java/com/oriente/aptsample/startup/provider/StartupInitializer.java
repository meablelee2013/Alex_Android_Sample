package com.oriente.aptsample.startup.provider;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.text.TextUtils;

import com.oriente.aptsample.startup.AndroidStartup;
import com.oriente.aptsample.startup.Startup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartupInitializer {

    public static String META_VALUE = "android.startup";

    public static List<AndroidStartup<?>> discoverAndInitialize(Context context, String providerName) throws PackageManager.NameNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Map<Class<? extends Startup>, AndroidStartup<?>> startups = new HashMap<>();
        //获取Manifest contentProvider 中的meta-data
        ComponentName provider = new ComponentName(context, providerName);
        ProviderInfo providerInfo = context.getPackageManager().getProviderInfo(provider, PackageManager.GET_META_DATA);
        //到得manifest中配置的任务
        for (String key : providerInfo.metaData.keySet()) {
            String value = providerInfo.metaData.getString(key);
            if (TextUtils.equals(META_VALUE, value)) {
                Class<?> clazz = Class.forName(key);
                if (Startup.class.isAssignableFrom(clazz)) {
                    //通过反射，通过类的class得到对象
                    doInitialize((AndroidStartup<?>) clazz.newInstance(), startups);
                }
            }
        }
        List<AndroidStartup<?>> result = new ArrayList<>(startups.values());
        return result;
    }

    private static void doInitialize(AndroidStartup<?> startup, Map<Class<? extends Startup>, AndroidStartup<?>> startups) throws IllegalAccessException, InstantiationException {
        startups.put(startup.getClass(), startup);
        if (startup.getDependenciesCount() != 0) {
            //遍历父任务
            for (Class<? extends Startup<?>> dependency : startup.dependencies()) {
                doInitialize((AndroidStartup<?>) dependency.newInstance(), startups);
            }
        }
    }


}
