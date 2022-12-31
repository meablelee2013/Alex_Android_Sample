package com.alex.nativelib

class NativeLib {

    /**
     * A native method that is implemented by the 'nativelib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun addInt(x: Int, y: Int): Int

    external fun testArrayAction(count: Int, textInfo: String, ints: IntArray, strs: Array<String>)

    companion object {
        // Used to load the 'nativelib' library on application startup.
        init {
            // System.load("") //加载绝对路径
            System.loadLibrary("nativelib")
        }
    }
}