package com.example.common;

import android.os.Build;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class AutoServiceUtil2 {

    private static volatile AutoServiceUtil2 instance;
    private ServiceLoader<BaseAction> serviceLoader;
    private Map<String, SoftReference<BaseAction>> serviceCache = new HashMap<>();

    private AutoServiceUtil2() {
        // Private constructor to prevent instantiation
    }

    public static AutoServiceUtil2 getInstance() {
        if (instance == null) {
            synchronized (AutoServiceUtil2.class) {
                if (instance == null) {
                    instance = new AutoServiceUtil2();
                }
            }
        }
        return instance;
    }

    public synchronized <T extends BaseAction> T getService(String serviceName, Class<T> clazz) {
        checkInitialized();

        // Check the cache first
        SoftReference<BaseAction> cachedServiceRef = serviceCache.get(serviceName);
        T cachedService = cachedServiceRef != null ? clazz.cast(cachedServiceRef.get()) : null;
        if (cachedService != null) {
            return cachedService;
        }

        // If not in the cache or the reference has been cleared, load it from ServiceLoader
        for (BaseAction action : serviceLoader) {
            if (clazz.isAssignableFrom(action.getClass()) && clazz.isInstance(action) && action.name().equals(serviceName)) {
                // Cache the service using a SoftReference
                serviceCache.put(serviceName, new SoftReference<>(action));
                return clazz.cast(action);
            }
        }

        // Log an error if the service is not found and throw an exception
        Log.e("AutoServiceUtil", "Error: Service not found: " + serviceName);
        throw new IllegalStateException("Service not found: " + serviceName);
    }

    public synchronized <T extends BaseAction> T getService(Class<T> clazz) {
        checkInitialized();

        // Check the cache first
        String serviceName = clazz != null ? clazz.getName() : "";
        SoftReference<BaseAction> cachedServiceRef = serviceCache.get(serviceName);
        T cachedService = cachedServiceRef != null ? clazz.cast(cachedServiceRef.get()) : null;
        if (cachedService != null) {
            return cachedService;
        }

        // If not in the cache, load it from ServiceLoader
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ServiceLoader<BaseAction> loader = ServiceLoader.load(BaseAction.class);
            for (BaseAction action : loader) {
                if (clazz.isAssignableFrom(action.getClass()) && clazz.isInstance(action)) {
                    // Cache the service using a SoftReference
                    serviceCache.put(serviceName, new SoftReference<>(action));
                    return clazz.cast(action);
                }
            }
        }

        // Log an error if the service is not found and throw an exception
        Log.e("AutoServiceUtil", "Error: Service not found: " + serviceName);
        throw new IllegalStateException("Service not found: " + serviceName);
    }

    private synchronized void checkInitialized() {
        if (serviceLoader == null || !serviceLoader.iterator().hasNext()) {
            serviceLoader = ServiceLoader.load(BaseAction.class);
        }
    }
}