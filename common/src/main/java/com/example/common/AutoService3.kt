package com.example.common

import android.os.Build
import android.util.Log
import java.lang.ref.SoftReference
import java.util.ServiceLoader

object AutoServiceUtil3 {

    private var serviceLoader: ServiceLoader<BaseAction>? = null
    private val serviceCache = HashMap<String, SoftReference<BaseAction>>()

    init {
        checkInitialized()
    }

    fun <T : BaseAction> getService(serviceName: String, clazz: Class<T>): T {
        // Check the cache first
        val cachedServiceRef = serviceCache[serviceName]
        val cachedService = cachedServiceRef?.get() as? T
        if (cachedService != null) {
            return cachedService
        }

        // If not in the cache or the reference has been cleared, load it from ServiceLoader
        for (action in serviceLoader!!) {
            if (clazz.isAssignableFrom(action.javaClass) && clazz.isInstance(action) && action.name() == serviceName) {
                // Cache the service using a SoftReference
                serviceCache[serviceName] = SoftReference(action)
                return action as T
            }
        }

        // Log an error if the service is not found and throw an exception
        Log.e("AutoServiceUtil", "Error: Service not found: $serviceName")
        throw IllegalStateException("Service not found: $serviceName")
    }

    fun <T : BaseAction> getService(clazz: Class<T>): T {
        // Check the cache first
        val serviceName = clazz.name
        val cachedServiceRef = serviceCache[serviceName]
        val cachedService = cachedServiceRef?.get() as? T
        if (cachedService != null) {
            return cachedService
        }

        // If not in the cache, load it from ServiceLoader
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val loader = ServiceLoader.load(BaseAction::class.java)
            for (action in loader) {
                if (clazz.isAssignableFrom(action.javaClass) && clazz.isInstance(action)) {
                    // Cache the service using a SoftReference
                    serviceCache[serviceName] = SoftReference(action)
                    return action as T
                }
            }
        }

        // Log an error if the service is not found and throw an exception
        Log.e("AutoServiceUtil", "Error: Service not found: $serviceName")
        throw IllegalStateException("Service not found: $serviceName")
    }

    private fun checkInitialized() {
        if (serviceLoader == null || !serviceLoader!!.iterator().hasNext()) {
            serviceLoader = ServiceLoader.load(BaseAction::class.java)
        }
    }
}
