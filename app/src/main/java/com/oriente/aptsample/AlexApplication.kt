package com.oriente.aptsample

import com.example.base.loadsir.BaseApplication
import com.example.base.loadsir.loadsir.*
import com.kingja.loadsir.core.LoadSir

class AlexApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .addCallback(TimeoutCallback())
            .addCallback(CustomCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }
}