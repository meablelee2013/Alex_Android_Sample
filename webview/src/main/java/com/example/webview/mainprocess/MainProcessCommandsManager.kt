package com.example.webview.mainprocess

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.base.loadsir.BaseApplication
import com.example.webview.IWebviewProcessToMainProcessInterface
import com.google.gson.Gson

const val TAG = "MainProcessCommandsManager"

object MainProcessCommandsManager : IWebviewProcessToMainProcessInterface.Stub() {


    override fun handleWebCommand(commandName: String?, jsonParmas: String?) {
        val fromJson = Gson().fromJson<Map<*, *>>(jsonParmas, MutableMap::class.java)
        if (fromJson is Map<*, *>) {
            executeCommand(commandName, fromJson as Map<String, Any>)
        }
    }

    @SuppressLint("LongLogTag")
    private fun executeCommand(commandName: String?, params: Map<String, Any>) {
        when (commandName) {
            "openPage" -> {
                val targetClass: String = params["target_class"].toString()
                if (!TextUtils.isEmpty(targetClass)) {
                    val intent = Intent()
                    intent.component = BaseApplication.sApplication?.let { ComponentName(it, targetClass) }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    BaseApplication.sApplication?.startActivity(intent)
                }

            }
            "showToast" -> {
                Log.d(TAG, "${Thread.currentThread() == Looper.getMainLooper().thread}")
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    val msg = params["message"].toString()
                    Toast.makeText(BaseApplication.sApplication, msg, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}