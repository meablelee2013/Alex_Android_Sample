package com.example.common

import java.util.ServiceLoader

object AutoServiceUtil {

    fun getService(name: String): BaseAction? {
        val load = ServiceLoader.load(BaseAction::class.java)
        load.forEach {
            if (it.name() == name) {
                return it
            }
            return null
        }
        return null
    }

    fun getService(clazz: Class<Any>): BaseAction? {
        val load = ServiceLoader.load(BaseAction::class.java)
        load.forEach {
            if (it == clazz) {
                return it
            }
            return null
        }
        return null
    }
}