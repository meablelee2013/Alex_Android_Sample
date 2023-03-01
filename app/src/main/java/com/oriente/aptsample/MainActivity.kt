package com.oriente.aptsample

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_2)
        Log.d("alex", "MainActivity.onCreate")
        title = "MainActivity"
//        findViewById<TextView>(R.id.checkHandler).setOnClickListener {
//            Log.d("alex","running process size = ${getRunningAppProcessInfoSize()}")
//            startActivity(Intent(this@MainActivity,SecondActivity::class.java))
//        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("alex", "MainActivity.onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("alex", "MainActivity.onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("alex", "MainActivity.onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("alex", "MainActivity.onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("alex", "MainActivity.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("alex", "MainActivity.onDestroy")
    }

    private fun getRunningAppProcessInfoSize(): Int {
        val systemService: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return systemService.runningAppProcesses.size
    }

}