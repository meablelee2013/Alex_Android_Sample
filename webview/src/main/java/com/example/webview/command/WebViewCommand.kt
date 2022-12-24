package com.example.webview.command

import com.example.webview.ICallbackFromMainprocessToWebViewProcessInterface

interface WebViewCommand {

    fun name(): String?

    fun execute(params: Map<*, *>?, callback: ICallbackFromMainprocessToWebViewProcessInterface)

}