package com.example.common

import android.os.Build
import java.util.*

object AutoServiceUtil {

    private lateinit var serviceLoader: ServiceLoader<BaseAction>

    fun getService(name: String): BaseAction? {
        checkInitialized()
        serviceLoader.forEach {
            if (it.name() == name) {
                return it
            }
        }
        return null
    }

    fun getService(clazz: Class<*>?): BaseAction? {
        checkInitialized()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val iterator = serviceLoader.iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (clazz != null && clazz.isAssignableFrom(next.javaClass)) {
                    return next
                }
            }
        }
        return null
    }

    private fun checkInitialized() {
        if (!this::serviceLoader.isInitialized) {
            serviceLoader = ServiceLoader.load(BaseAction::class.java)
        }
    }
}