package com.oriente.aptsample.sample2.network.callback

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType

abstract class HttpCallback<Result> : ICallback {
    var mGson: Gson = Gson()
    override fun onSuccess(result: String?) {
        // result就是网络访问第三方框架返回的字符串
        // 1.得到调用者用什么样的javaBean接收数据
        val clz = analysisClassInfo(this)
        // 2.把String result转成对应的javaBean
        val fromJson = mGson.fromJson(result, clz)
        if (fromJson != null) {
            val objResult = fromJson as Result
            // 3.objResult交给程序员
            onSuccess(objResult)
        }
    }

    abstract fun onSuccess(objResult: Result)


    override fun onFailure(e: String?) {}


    private fun analysisClassInfo(result: Any): Class<*> {
        // getGenericSuperclass();
        // 可以得到包含原始类型，参数化类型，数组，类型变量，基本数据类型
        val getType = result.javaClass.genericSuperclass
        val params = (getType as ParameterizedType).actualTypeArguments
        return params[0] as Class<*> // <>里面只有一个，所以取0号元素即可
    }
}
