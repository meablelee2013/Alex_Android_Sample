package com.example.webview

interface WebViewCallBack {

    fun onPageStarted(url: String?)

    fun onPageFinished(url: String?)

    fun onError()

    fun onReceivedTitle(title: String?)
}