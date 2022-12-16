package com.oriente.aptsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toMaxMB = SizeUnit.BYTE.toMB(Runtime.getRuntime().maxMemory())
        val toTotalMB = SizeUnit.BYTE.toMB(Runtime.getRuntime().totalMemory())
        Log.d("alex", "max memory $toMaxMB  total memory $toTotalMB")

    }
}