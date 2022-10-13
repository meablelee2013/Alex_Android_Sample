package com.oriente.aptsample

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.oriente.aptsample.databinding.ActivityMainBinding

const val TAG = "alex"

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = MainViewModel(this)
        LiveDataBus.get().with("message", String::class.java)
            .observe(this, Observer {
                Log.d(TAG, "first activity value change: ${it}")
            })


    }
}