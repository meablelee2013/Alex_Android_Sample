package com.oriente.aptsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DispatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "DispatchActivity"
        val intent = intent
        if (intent != null) {
            val uri = intent.data
        }
    }
}