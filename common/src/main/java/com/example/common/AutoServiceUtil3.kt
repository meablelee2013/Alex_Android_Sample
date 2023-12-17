package com.example.common

import android.os.Build
import android.util.Log
import java.lang.ref.SoftReference
import java.util.*

object AutoServiceUtil3 {

    lateinit var serviceLoader: ServiceLoader<BaseAction>
    val serviceCache: MutableMap<String, SoftReference<BaseAction>> = mutableMapOf()

    inline fun <reified T : BaseAction> getService(serviceName: String, clazz: Class<T>): T {
        checkInitialized()

        // Check the cache first
        val cachedServiceRef = serviceCache[serviceName]
        val cachedService = cachedServiceRef?.get() as? T
        if (cachedService != null) {
            return cachedService
        }

        // If not in the cache or the reference has been cleared, load it from ServiceLoader
        serviceLoader.forEach {
            if (clazz.isAssignableFrom(it.javaClass) && it is T && it.name() == serviceName) {
                // Cache the service using a SoftReference
                serviceCache[serviceName] = SoftReference(it)
                return it
            }
        }

        // Log an error if the service is not found and throw an exception
        Log.e("AutoServiceUtil", "Error: Service not found: $serviceName")
        throw IllegalStateException("Service not found: $serviceName")
    }

    inline fun <reified T : BaseAction> getService(clazz: Class<T>?): T {
        checkInitialized()

        // Check the cache first
        val serviceName = clazz?.name ?: ""
        val cachedServiceRef = serviceCache[serviceName]
        val cachedService = cachedServiceRef?.get() as? T
        if (cachedService != null) {
            return cachedService
        }

        // If not in the cache, load it from ServiceLoader
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val iterator = serviceLoader.iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (clazz != null && clazz.isAssignableFrom(next.javaClass) && next is T) {
                    // Cache the service using a SoftReference
                    serviceCache[serviceName] = SoftReference(next)
                    return next
                }
            }
        }

        // Log an error if the service is not found and throw an exception
        Log.e("AutoServiceUtil", "Error: Service not found: $serviceName")
        throw IllegalStateException("Service not found: $serviceName")
    }

    fun checkInitialized() {
        if (!this::serviceLoader.isInitialized) {
            serviceLoader = ServiceLoader.load(BaseAction::class.java)
        }
    }
}