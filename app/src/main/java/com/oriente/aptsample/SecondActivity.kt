package com.oriente.aptsample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer

class SecondActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondactivity)

        LiveDataBus.get().with("message", String::class.java)
            .observe(this, Observer {
                Log.d("alex", "second activity value change: ${it}")
            })

        findViewById<Button>(R.id.sendMessageBtn).setOnClickListener {
            LiveDataBus.get().with("message").value = "from secondActivity"
        }

        findViewById<Button>(R.id.sendMessageAgainBtn).setOnClickListener {
            LiveDataBus.get().with("message").value = "from secondActivity again"
        }
    }
}