package com.oriente.aptsample

/**
 * 用于标记viewmodel的作用域
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class VMScope(val scopeName:String) {
}