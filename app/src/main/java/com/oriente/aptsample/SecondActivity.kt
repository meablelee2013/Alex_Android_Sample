package com.oriente.aptsample

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("alex", "SecondActivity.onCreate")
        title = "SecondActivity"

        findViewById<TextView>(R.id.checkHandler).setOnClickListener {
            Log.e("alex", "running process size = ${getRunningAppProcessInfoSize()}")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e("alex", "SecondActivity.onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("alex", "SecondActivity.onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("alex", "SecondActivity.onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("alex", "SecondActivity.onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("alex", "SecondActivity.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("alex", "SecondActivity.onDestroy")
    }


    private fun getRunningAppProcessInfoSize(): Int {
        val systemService: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return systemService.runningAppProcesses.size
    }

}