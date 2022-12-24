package com.example.webview.webviewprocess

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.webview.WebViewCallBack
import com.example.webview.bean.JsParam
import com.example.webview.webviewprocess.settings.AlexWebViewSetting
import com.example.webview.webviewprocess.webchromeclient.AlexWebChromeClient
import com.example.webview.webviewprocess.webviewclient.AlexWebViewClient
import com.google.gson.Gson


class BaseWebView : WebView, LifecycleEventObserver {
    var gson: Gson = Gson()

    private val mBlankMonitorRunnable by lazy { BlankMonitorRunnable() }

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
        WebViewProcessCommandDispatcher.startAidlConnection()
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
            Log.i("BaseWebView", jsParam)
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
    /**
     * 调用后
     * 5s 后开始执行白屏检测任务 时间可以适当修改
     */
    fun postBlankMonitorRunnable() {
        Log.d("BaseWebView", "白屏检测任务 5s 后执行")
        removeCallbacks(mBlankMonitorRunnable)
        postDelayed(mBlankMonitorRunnable, 5000)
    }

    /**
     * 取消白屏检测任务
     */
    fun removeBlankMonitorRunnable() {
        Log.d("BaseWebView", "白屏检测任务取消执行")
        removeCallbacks(mBlankMonitorRunnable)
    }


    fun unBind() {
        WebViewProcessCommandDispatcher.unBind()
    }

    override fun canGoBack(): Boolean {
        val backForwardList = copyBackForwardList()
        val currentIndex = backForwardList.currentIndex - 1
        if (currentIndex >= 0) {
            val item = backForwardList.getItemAtIndex(currentIndex)
            if (item?.url == "about:blank") {
                return false
            }
        }
        return super.canGoBack()
    }

    /**
     * 设置 WebView 生命管控（自动回调生命周期方法）
     */
    fun setLifecycleOwner(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }


    /**
     * 生命周期回调
     */
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_STOP -> onPause()
            Lifecycle.Event.ON_DESTROY -> {
                source.lifecycle.removeObserver(this)
                onDestroy()
            }
            else -> {}
        }
    }

    /**
     * 生命周期 onResume()
     */
    override fun onResume() {
        super.onResume()
        settings.javaScriptEnabled = true
    }

    /**
     * 生命周期 onDestroy()
     * 父类没有 需要自己写
     */
    private fun onDestroy() {
        settings.javaScriptEnabled = false
    }

    interface BlankMonitorCallback {
        fun onBlank()
    }

    private var mBlankMonitorCallback: BlankMonitorCallback? = null

    fun setBlankMonitorCallback(callback: BlankMonitorCallback){
        this.mBlankMonitorCallback = callback
    }

    inner class BlankMonitorRunnable : Runnable {

        override fun run() {
            val task = Thread {
                // 根据宽高的 1/6 创建 bitmap
                val dstWidth = measuredWidth / 6
                val dstHeight = measuredHeight / 6
                val snapshot = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888)
                // 绘制 view 到 bitmap
                val canvas = Canvas(snapshot)
                draw(canvas)

                // 像素点总数
                val pixelCount = (snapshot.width * snapshot.height).toFloat()
                var whitePixelCount = 0 // 白色像素点计数
                var otherPixelCount = 0 // 其他颜色像素点计数
                // 遍历 bitmap 像素点
                for (x in 0 until snapshot.width) {
                    for (y in 0 until snapshot.height) {
                        // 计数 其实记录一种就可以
                        if (snapshot.getPixel(x, y) == -1) {
                            whitePixelCount++
                        }else{
                            otherPixelCount++
                        }
                    }
                }
                // 回收 bitmap
                snapshot.recycle()

                if (whitePixelCount == 0) {
                    return@Thread
                }

                // 计算白色像素点占比 （计算其他颜色也一样）
                val percentage: Float = whitePixelCount / pixelCount * 100
                // 如果超过阈值 触发白屏提醒
                if (percentage > 95) {
                    post {
                        mBlankMonitorCallback?.onBlank()
                    }
                }
            }
            task.start()
        }
    }

    /**
     * 释放资源操作
     */
    fun release() {
        (parent as ViewGroup?)?.removeView(this)
        removeAllViews()
        stopLoading()
        loadUrl("about:blank")
        clearHistory()
    }


}