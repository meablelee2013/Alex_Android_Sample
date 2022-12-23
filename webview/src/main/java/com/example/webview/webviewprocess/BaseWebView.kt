package com.example.webview.webviewprocess

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.example.webview.WebViewCallBack
import com.example.webview.webviewprocess.settings.AlexWebViewSetting
import com.example.webview.webviewprocess.webchromeclient.AlexWebChromeClient
import com.example.webview.webviewprocess.webviewclient.AlexWebViewClient


class BaseWebView : WebView {
    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context!!, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
//        WebViewProcessCommandDispatcher.getInstance().initAidlConnection()
        AlexWebViewSetting.setSettings(this)
        addJavascriptInterface(this, "alex")
    }

    fun registerWebViewCallBack(webViewCallBack: WebViewCallBack?) {
        webViewClient = AlexWebViewClient(webViewCallBack)
        webChromeClient = AlexWebChromeClient(webViewCallBack)
    }

    @JavascriptInterface
    fun takeNativeAction(jsParam: String?) {
        Log.i(TAG, jsParam!!)
//        if (!TextUtils.isEmpty(jsParam)) {
//            val jsParamObject: JsParam = Gson().fromJson(jsParam, JsParam::class.java)
//            if (jsParamObject != null) {
//                WebViewProcessCommandDispatcher.getInstance().executeCommand(jsParamObject.name, Gson().toJson(jsParamObject.param), this)
//            }
//        }
    }

    fun handleCallback(callbackname: String, response: String) {
        if (!TextUtils.isEmpty(callbackname) && !TextUtils.isEmpty(response)) {
            post {
                val jscode = "javascript:xiangxuejs.callback('$callbackname',$response)"
                Log.e("xxxxxx", jscode)
                evaluateJavascript(jscode, null)
            }
        }
    }

    companion object {
        const val TAG = "XiangxueWebView"
    }
}