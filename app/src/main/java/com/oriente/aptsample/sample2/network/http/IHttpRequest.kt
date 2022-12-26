package com.oriente.aptsample.sample2.network.http

import com.oriente.aptsample.sample2.network.callback.ICallback

interface IHttpRequest {

    fun post(url: String, params: Map<String, Any>, callback: ICallback)

    fun get(url: String, callback: ICallback)
}