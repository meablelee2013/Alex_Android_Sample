package com.oriente.aptsample.commandimpl

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.base.loadsir.BaseApplication
import com.example.webview.ICallbackFromMainprocessToWebViewProcessInterface
import com.example.webview.command.WebViewCommand
import com.google.auto.service.AutoService


@AutoService(WebViewCommand::class)
class CommandOpenSchema : WebViewCommand {
    override fun name(): String? {
        return "openSchema"
    }

    override fun execute(params: Map<*, *>?, callback: ICallbackFromMainprocessToWebViewProcessInterface) {

        val schema: String = params?.get("schema").toString()
        Log.d("CommandOpenSchema", schema)
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(schema)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        BaseApplication.sApplication?.startActivity(intent)
    }
}