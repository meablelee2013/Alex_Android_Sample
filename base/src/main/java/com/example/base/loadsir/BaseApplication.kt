package com.example.base.loadsir

import android.app.Application

open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        sApplication = this
    }

    companion object {
        var sApplication: Application? = null
    }
}