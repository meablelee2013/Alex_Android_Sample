package com.oriente.aptsample.sample2.network.callback

interface ICallback {

    fun onSuccess(result: String?)

    fun onFailure(e: String?)

}