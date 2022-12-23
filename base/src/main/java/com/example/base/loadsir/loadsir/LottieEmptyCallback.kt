package com.example.base.loadsir.loadsir

import com.example.base.R
import com.kingja.loadsir.callback.Callback

class LottieEmptyCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_lottie_empty
    }
}