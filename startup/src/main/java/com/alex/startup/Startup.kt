package com.alex.startup

import android.content.Context

interface Startup<T> : Dispatcher {

    fun create(context: Context): T

    fun dependencies(): List<Class<out Startup<*>?>?>?

    fun getDependenciesCount(): Int

}