package com.oriente.aptsample.sample1.injectinterface

import android.util.Log
import javax.inject.Inject

class TestInterfaceImpl @Inject constructor() : TestInterface {

    override fun doSomeThing() {
        Log.d("alex", "do something")
    }

}