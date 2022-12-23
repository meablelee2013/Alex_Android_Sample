package com.example.webview

import android.content.Context
import android.content.Intent
import com.example.common.autoservice.IWebViewService
import com.example.webview.utils.ANDROID_ASSET_URI
import com.example.webview.utils.IS_SHOW_ACTION_BAR
import com.example.webview.utils.TITLE
import com.example.webview.utils.URL
import com.google.auto.service.AutoService

@AutoService(IWebViewService::class)
class WebViewServiceImpl : IWebViewService {
    override fun startWebViewActivity(context: Context, url: String, title: String, isShowActionBar: Boolean) {
        if (context != null) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(URL, url)
            intent.putExtra(TITLE, title)
            intent.putExtra(IS_SHOW_ACTION_BAR, isShowActionBar)
            context.startActivity(intent)
        }
    }

    override fun startDemoHtml(context: Context?) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra(TITLE, "本地Demo测试页")
        intent.putExtra(URL, ANDROID_ASSET_URI + "demo.html")
        context!!.startActivity(intent)
    }
}