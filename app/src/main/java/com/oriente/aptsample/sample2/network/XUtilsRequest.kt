package com.oriente.aptsample.sample2.network

import android.app.Application
import com.oriente.aptsample.sample2.network.callback.ICallback
import com.oriente.aptsample.sample2.network.http.IHttpRequest
import org.xutils.common.Callback.CancelledException
import org.xutils.common.Callback.CommonCallback
import org.xutils.http.RequestParams
import org.xutils.x
import javax.inject.Inject

class XUtilsRequest @Inject constructor(var application: Application) : IHttpRequest {
    init {
        x.Ext.init(application)
    }

    override fun post(url: String, params: Map<String, Any>, callback: ICallback) {
        val requestParams = RequestParams(url)
        x.http().post(requestParams, object : CommonCallback<String?> {
            override fun onSuccess(result: String?) {
                callback.onSuccess(result)
            }

            override fun onError(ex: Throwable, isOnCallback: Boolean) {}
            override fun onCancelled(cex: CancelledException) {}
            override fun onFinished() {}
        })
    }

    override operator fun get(url: String, callback: ICallback) {
        val requestParams = RequestParams(url)
        x.http().get(requestParams, object : CommonCallback<String?> {
            override fun onSuccess(result: String?) {
                callback.onSuccess(result)
            }

            override fun onError(ex: Throwable, isOnCallback: Boolean) {}
            override fun onCancelled(cex: CancelledException) {}
            override fun onFinished() {}
        })
    }
}