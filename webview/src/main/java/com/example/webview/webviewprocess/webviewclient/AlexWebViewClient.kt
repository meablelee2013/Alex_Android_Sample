package com.example.webview.webviewprocess.webviewclient

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.webkit.*
import com.bumptech.glide.Glide
import com.example.webview.WebViewCallBack
import com.example.webview.apiservice.FileApiService
import com.example.webview.utils.WebUtil
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import java.io.File

const val TAG = "AlexWebViewClient"

/**
 * 处理WebView
 */
class AlexWebViewClient(var webViewCallBack: WebViewCallBack?) : WebViewClient() {


    private val fileApiService by lazy {
        Retrofit.Builder().build().create(FileApiService::class.java)
    }

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

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        return shouldOverrideUrl(view, request.url)
    }

    private fun shouldOverrideUrl(view: WebView, url: Uri): Boolean {
        val scheme = url.scheme
        if (!TextUtils.isEmpty(scheme)) {
            when (scheme) {
                "http", "https" -> {
                    view.loadUrl(url.toString())
                }
            }
        }
        return true
    }

    override fun shouldInterceptRequest(
        view: WebView, request: WebResourceRequest
    ): WebResourceResponse? {
        var webResourceResponse: WebResourceResponse? = null

        // 如果是 assets 目录下的文件
        if (isAssetsResource(request)) {
            webResourceResponse = assetsResourceRequest(view.context, request)
        }

        // 如果是可以缓存的文件
        if (isCacheResource(request)) {
            webResourceResponse = cacheResourceRequest(view.context, request)
        }

        if (webResourceResponse == null) {
            webResourceResponse = super.shouldInterceptRequest(view, request)
        }
        return webResourceResponse
    }

    private fun isAssetsResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        return url.startsWith("file:///android_asset/")
    }


    /**
     * assets 文件请求
     */
    private fun assetsResourceRequest(
        context: Context, webRequest: WebResourceRequest
    ): WebResourceResponse? {
        val url = webRequest.url.toString()
        try {
            val filenameIndex = url.lastIndexOf("/") + 1
            val filename = url.substring(filenameIndex)
            val suffixIndex = url.lastIndexOf(".")
            val suffix = url.substring(suffixIndex + 1)
            val webResourceResponse = WebResourceResponse(
                getMimeTypeFromUrl(url), "UTF-8", context.assets.open("$suffix/$filename")
            )
            webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
            return webResourceResponse
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 判断是否是可以被缓存等资源
     */
    private fun isCacheResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        return extension == "ico" || extension == "bmp" || extension == "gif" || extension == "jpeg" || extension == "jpg" || extension == "png" || extension == "svg" || extension == "webp" || extension == "css" || extension == "js" || extension == "json" || extension == "eot" || extension == "otf" || extension == "ttf" || extension == "woff"
    }

    /**
     * 可缓存文件请求
     */
    private  fun cacheResourceRequest(
        context: Context, webRequest: WebResourceRequest
    ): WebResourceResponse? {
        var url = webRequest.url.toString()
        var mimeType = getMimeTypeFromUrl(url)

        // WebView 中的图片利用 Glide 加载(能够和App其他页面共用缓存)
        if (isImageResource(webRequest)) {
            return try {
                val file = Glide.with(context).download(url).submit().get()
                val webResourceResponse = WebResourceResponse(mimeType, "UTF-8", file.inputStream())
                webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
                webResourceResponse
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        /**
         * 其他文件缓存逻辑
         * 1.寻找缓存文件，本地有缓存直接返回缓存文件
         * 2.无缓存，下载到本地后返回
         * 注意！！！
         * 一定要确保文件下载完整，我这里采用下载完成后给文件加 "success-" 前缀的方法
         */
        val webCachePath = WebUtil.getWebViewCachePath(context)
        val currentTimeMillis = System.currentTimeMillis()
        val cacheFilePath = webCachePath + File.separator + "success-" + currentTimeMillis // 自定义文件命名规则
        val cacheFile = File(cacheFilePath)
        if (!cacheFile.exists() || !cacheFile.isFile) { // 本地不存在 则开始下载
            // 下载文件
            val sourceFilePath = webCachePath + File.separator + currentTimeMillis
            val sourceFile = File(sourceFilePath)
            runBlocking {
                try {
//                    fileApiService.download(url, webRequest.requestHeaders).use {
//                        it.byteStream().use { inputStream ->
//                            sourceFile.writeBytes(inputStream.readBytes())
//                        }
//                    }
                    // 下载完成后增加 "success-" 前缀 代表文件无损 【防止io流被异常中断导致文件损坏 无法判断】
                    sourceFile.renameTo(cacheFile)
                } catch (e: Exception) {
                    e.printStackTrace()
                    // 发生异常删除文件
                    sourceFile.deleteOnExit()
                    cacheFile.deleteOnExit()
                }
            }
        }

        // 缓存文件存在则返回
        if (cacheFile.exists() && cacheFile.isFile) {
            val webResourceResponse = WebResourceResponse(mimeType, "UTF-8", cacheFile.inputStream())
            webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
            return webResourceResponse
        }
        return null
    }

    /**
     * 判断是否是图片
     * 有些文件存储没有后缀，也可以根据自家服务器域名等等
     */
    private fun isImageResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        return extension == "ico" || extension == "bmp" || extension == "gif" || extension == "jpeg" || extension == "jpg" || extension == "png" || extension == "svg" || extension == "webp"
    }

    /**
     * 根据 url 获取文件类型
     */
    private fun getMimeTypeFromUrl(url: String): String {
        try {
            val extension = MimeTypeMap.getFileExtensionFromUrl(url)
            if (extension.isNotBlank() && extension != "null") {
                if (extension == "json") {
                    return "application/json"
                }
                return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "*/*"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "*/*"
    }


}