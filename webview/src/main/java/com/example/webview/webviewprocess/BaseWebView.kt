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

    private fun init() {
        WebViewProcessCommandDispatcher.initAidlConnection()
        AlexWebViewSetting.setSettings(this)
        addJavascriptInterface(this, "alexWebView")
    }

    fun registerWebViewCallBack(webViewCallBack: WebViewCallBack?) {
        webViewClient = AlexWebViewClient(webViewCallBack)
        webChromeClient = AlexWebChromeClient(webViewCallBack)
    }

    @JavascriptInterface
    fun takeNativeAction(jsParam: String?) {
        if (jsParam != null) {
            Log.i(TAG, jsParam)
        }
        if (!TextUtils.isEmpty(jsParam)) {
            val jsParamObject: JsParam = Gson().fromJson(jsParam, JsParam::class.java)
            if (jsParamObject != null) {
                WebViewProcessCommandDispatcher.executeCommand(jsParamObject.name, gson.toJson(jsParamObject.param))
            }
        }
    }

    fun handleCallback(callbackname: String, response: String) {
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