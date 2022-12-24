package com.example.webview.webviewprocess

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.example.webview.WebViewCallBack
import com.example.webview.bean.JsParam
import com.example.webview.webviewprocess.settings.AlexWebViewSetting
import com.example.webview.webviewprocess.webchromeclient.AlexWebChromeClient
import com.example.webview.webviewprocess.webviewclient.AlexWebViewClient
import com.google.gson.Gson


class BaseWebView : WebView {
    var gson: Gson = Gson()

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        init()
    }

    /**
     *  alexWebView 这个对象会挂载在html的window上 ,在html的js里可以直接通过window.alexWebView.callNativeAction 来调用
     *  Native的方法
     */
    private fun init() {
        //在WebView
        WebViewProcessCommandDispatcher.initAidlConnection()
        AlexWebViewSetting.setSettings(this)
        addJavascriptInterface(this, "alexWebView")
    }

    fun registerWebViewCallBack(webViewCallBack: WebViewCallBack?) {
        webViewClient = AlexWebViewClient(webViewCallBack)
        webChromeClient = AlexWebChromeClient(webViewCallBack)
    }

    /**
     * 暴露给h5调用的方法
     */
    @JavascriptInterface
    fun callNativeAction(jsParam: String?) {
        if (jsParam != null) {
            Log.i(TAG, jsParam)
        }
        if (!TextUtils.isEmpty(jsParam)) {
            val jsParamObject: JsParam = Gson().fromJson(jsParam, JsParam::class.java)
            if (jsParamObject != null) {
                WebViewProcessCommandDispatcher.executeCommand(jsParamObject.name, gson.toJson(jsParamObject.param), this)
            }
        }
    }

    fun handleCallback(callbackname: String?, response: String?) {
        if (!TextUtils.isEmpty(callbackname) && !TextUtils.isEmpty(response)) {
            post {
                val jscode = "javascript:alexjs.callback('$callbackname',$response)"
                Log.e("xxxxxx", jscode)
                evaluateJavascript(jscode, null)
            }
        }
    }

    companion object {
        const val TAG = "AlexWebView"
    }
}