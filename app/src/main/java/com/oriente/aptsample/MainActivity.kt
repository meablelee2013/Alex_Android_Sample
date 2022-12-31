package com.oriente.aptsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alex.nativelib.NativeLib
import com.oriente.aptsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.checkHandler.setOnClickListener {
            val nativeLib = NativeLib()
            println("nativeLib.addInt  ${nativeLib.addInt(1, 2)}")
        }
    }
}