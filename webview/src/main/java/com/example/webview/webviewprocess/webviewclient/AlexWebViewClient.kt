package com.example.webview.webviewprocess.webviewclient

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.webview.WebViewCallBack

const val TAG = "AlexWebViewClient"

/**
 * 处理WebView
 */
class AlexWebViewClient(var webViewCallBack: WebViewCallBack?) : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        if (webViewCallBack != null) {
            webViewCallBack!!.onPageStarted(url)
        } else {
            Log.d(TAG, "webViewCallBack cannot be null")
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if (webViewCallBack != null) {
            webViewCallBack!!.onPageFinished(url)
        } else {
            Log.d(TAG, "webViewCallBack cannot be null")
        }
    }


    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        if (webViewCallBack != null) {
            webViewCallBack!!.onError()
        } else {
            Log.d(TAG, "webViewCallBack cannot be null")
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }

}