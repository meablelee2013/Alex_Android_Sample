package com.example.webview

import android.content.Context
import android.content.Intent
import com.example.common.autoservice.IWebViewService
import com.google.auto.service.AutoService

@AutoService(IWebViewService::class)
class WebViewServiceImpl : IWebViewService {
    override fun startWebViewActivity(context: Context, url: String, title: String, isShowActionBar: Boolean) {
        context.startActivity(Intent(context, WebViewActivity::class.java))
    }
}