package com.alex.nativelib

class NativeLib {

    /**
     * A native method that is implemented by the 'nativelib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun addInt(x: Int, y: Int): Int

    companion object {
        // Used to load the 'nativelib' library on application startup.
        init {
            System.loadLibrary("nativelib")
        }
    }
}