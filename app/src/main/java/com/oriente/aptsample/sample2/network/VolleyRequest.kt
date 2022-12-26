package com.oriente.aptsample.sample2.network

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.oriente.aptsample.sample2.network.callback.ICallback
import com.oriente.aptsample.sample2.network.http.IHttpRequest
import javax.inject.Inject

class VolleyRequest @Inject constructor(var context: FragmentActivity) : IHttpRequest {
    var mQueue: RequestQueue = Volley.newRequestQueue(context)


    override fun post(url: String, params: Map<String, Any>, callback: ICallback) {
        val stringRequest = StringRequest(Request.Method.POST, url, { response -> callback.onSuccess(response) }) { }
        mQueue.add(stringRequest)
    }

    override fun get(url: String, callback: ICallback) {
        val stringRequest = StringRequest(Request.Method.GET, url, { response -> callback.onSuccess(response) }) { }
        mQueue.add(stringRequest)
    }
}