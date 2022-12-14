package com.oriente.aptsample.learnkotlin.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.reflect.Proxy


fun main() {
    GlobalScope.launch {
        val newProxyInstance = Proxy.newProxyInstance(
            IRent::class.java.classLoader, listOf(IRent::class.java).toTypedArray()
        ) { proxy, method, args ->
            val annotations = method.annotations
            val parameterTypes = method.parameterTypes
            val parameterAnnotations = method.parameterAnnotations


            null

        }
        val rent: IRent = newProxyInstance as IRent
        rent.rent("haha")
    }

}