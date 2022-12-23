package com.example.webview.webviewprocess.webchromeclient

import android.util.Log
import android.webkit.*
import com.example.webview.WebViewCallBack

const val TAG = "AlexWebChromeClient"

class AlexWebChromeClient(var webViewCallBack: WebViewCallBack?) : WebChromeClient() {
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        if (webViewCallBack != null) {
            webViewCallBack!!.onReceivedTitle(title)
        } else {
            Log.d(TAG, "webViewCallBack can not be null")
        }
    }

    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        return super.onJsAlert(view, url, message, result)
    }

    override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        return super.onJsConfirm(view, url, message, result)
    }

    override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
        return super.onJsPrompt(view, url, message, defaultValue, result)
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        consoleMessage?.message()?.let { Log.d(TAG, it) }
        return super.onConsoleMessage(consoleMessage)
    }
}