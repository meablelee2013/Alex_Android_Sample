package com.oriente.aptsample.commandimpl

import android.content.ComponentName
import android.content.Intent
import android.text.TextUtils
import com.example.base.loadsir.BaseApplication
import com.example.webview.ICallbackFromMainprocessToWebViewProcessInterface
import com.example.webview.command.WebViewCommand
import com.google.auto.service.AutoService

@AutoService(WebViewCommand::class)
class CommandOpenPage : WebViewCommand {
    override fun name(): String? {
        return "openPage"
    }

    override fun execute(params: Map<*, *>?, callback: ICallbackFromMainprocessToWebViewProcessInterface) {
        val targetClass: String = params?.get("target_class").toString()
        if (!TextUtils.isEmpty(targetClass)) {
            val intent = Intent()
            intent.component = BaseApplication.sApplication?.let { ComponentName(it, targetClass) }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            BaseApplication.sApplication?.startActivity(intent)
        }
    }
}