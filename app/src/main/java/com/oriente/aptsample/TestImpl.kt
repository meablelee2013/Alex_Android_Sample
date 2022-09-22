package com.oriente.aptsample

import android.util.Log
import javax.inject.Inject

class TestImpl @Inject constructor() : TestInterface {
    override fun doSomeThing() {
        Log.d("alex", "do something")
    }
}