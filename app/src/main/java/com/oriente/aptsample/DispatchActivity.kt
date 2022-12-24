package com.oriente.aptsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.oriente.aptsample.databinding.ActivityDispatchBinding

class DispatchActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityDispatchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dispatch)
        title = "这是一个Schema页面"
        val intent = intent
        if (intent != null) {
            val uri = intent.data
        }
    }
}