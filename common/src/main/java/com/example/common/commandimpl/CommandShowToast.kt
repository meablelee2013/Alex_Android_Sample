package com.example.common.commandimpl

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.base.loadsir.BaseApplication
import com.example.base.loadsir.command.Command
import com.google.auto.service.AutoService


const val TAG = "CommandShowToast"

@AutoService(Command::class)
class CommandShowToast : Command {
    override fun name(): String? {
        return "showToast"
    }

    override fun execute(params: Map<*, *>) {
        Log.d(TAG, "${Thread.currentThread() == Looper.getMainLooper().thread}")
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val msg = params["message"].toString()
            Toast.makeText(BaseApplication.sApplication, msg, Toast.LENGTH_LONG).show()
        }
    }
}