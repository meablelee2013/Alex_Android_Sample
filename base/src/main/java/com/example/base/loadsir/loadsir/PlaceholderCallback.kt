package com.example.base.loadsir.loadsir

import android.content.Context
import android.view.View
import com.example.base.R
import com.kingja.loadsir.callback.Callback

class PlaceholderCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_placeholder
    }

    override fun onReloadEvent(context: Context, view: View): Boolean {
        return true
    }
}