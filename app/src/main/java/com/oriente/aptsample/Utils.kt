package com.oriente.aptsample

import android.app.Activity
import android.widget.Toast

object Utils {
    fun Activity.showToast(msg: String, time: Int) {
        Toast.makeText(this, msg, time).show()
    }
}