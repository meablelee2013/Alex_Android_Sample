package com.example.common.autoservice

import android.content.Context

interface IWebViewService {
    fun startWebViewActivity(context: Context, url: String, title: String, isShowActionBar: Boolean)
}