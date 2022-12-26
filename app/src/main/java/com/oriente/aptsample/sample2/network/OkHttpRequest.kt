package com.oriente.aptsample.sample2.network

import android.os.Handler
import android.os.Looper
import com.oriente.aptsample.sample2.network.callback.ICallback
import com.oriente.aptsample.sample2.network.http.IHttpRequest
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class OkHttpRequest @Inject constructor() : IHttpRequest {
    var okHttpClient: OkHttpClient = OkHttpClient()
    var myHandler: Handler = Handler(Looper.getMainLooper())


    override fun post(url: String, params: Map<String, Any>, callback: ICallback) {
        val requestBody: RequestBody = appendBody(params)
        val request = Request.Builder().url(url).post(requestBody).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val result = response.body.toString()
                if (response.isSuccessful) {
                    myHandler.post {
                        callback.onSuccess(result)
                    }
                } else {
                    myHandler.post {
                        callback.onFailure(result)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                myHandler.post {
                    callback.onFailure("onFailure")
                }
            }
        })
    }

    override fun get(url: String, callback: ICallback) {
        val request = Request.Builder().url(url).get().build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val result = response.body.toString()
                if (response.isSuccessful) {
                    myHandler.post {
                        callback.onSuccess(result)
                    }
                } else {
                    myHandler.post {
                        callback.onFailure(result)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                myHandler.post {
                    callback.onFailure("onFailure")
                }
            }
        })
    }

    private fun appendBody(params: Map<String, Any>?): RequestBody {
        val body = FormBody.Builder()
        if (params == null || params.isEmpty()) {
            return body.build()
        }

        for ((key, value) in params) {
            body.add(key, value.toString())
        }
        return body.build()
    }
}