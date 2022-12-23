package com.oriente.aptsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.common.AutoServiceManager
import com.example.common.autoservice.IWebViewService
import com.oriente.aptsample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.btn21.setOnClickListener {

//             AutoServiceManager.load(IWebViewService::class.java)?.startWebViewActivity(this, "https://www.baidu.com", "百度一下", true)
            AutoServiceManager.load(IWebViewService::class.java)?.startDemoHtml(this)
        }
    }
}


