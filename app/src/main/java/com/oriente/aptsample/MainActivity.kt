package com.oriente.aptsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.common.autoservice.IWebViewService
import com.example.webview.WebViewActivity
import com.oriente.aptsample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import java.util.ServiceLoader

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.btn21.setOnClickListener {
            val next = ServiceLoader.load(IWebViewService::class.java).iterator().next()
            next.startWebViewActivity(this, "https://www.baidu.com", "百度一下", true)
        }

    }
}


