package com.example.webview.mainprocess

import android.annotation.SuppressLint
import com.example.webview.ICallbackFromMainprocessToWebViewProcessInterface
import com.example.webview.IWebViewProcessToMainProcessInterface
import com.example.webview.command.WebViewCommand
import com.google.gson.Gson
import java.util.*

/**
 *
 * 处理来自html的各种请求（html-->native）
 * 主进程给WebView进程提供服务，需要继承Stub类
 */
object MainProcessCommandsManager : IWebViewProcessToMainProcessInterface.Stub() {
    private val mWebViewCommands = HashMap<String, WebViewCommand>()
    private val mGson = Gson()

    init {
        val serviceLoader: ServiceLoader<WebViewCommand> = ServiceLoader.load(WebViewCommand::class.java)
        if (serviceLoader != null) {
            for (command in serviceLoader) {
                if (!mWebViewCommands.containsKey(command.name())) {
                    mWebViewCommands[command.name()!!] = command
                }
            }
        }
    }

    /**
     * 处理来自html的各种请求
     */
    override fun handleWebCommand(commandName: String?, jsonParmas: String?, callback: ICallbackFromMainprocessToWebViewProcessInterface) {
        executeCommand(
            commandName,
            mGson.fromJson<Map<*, *>>(
                jsonParmas,
                MutableMap::class.java
            ), callback
        )
    }

    @SuppressLint("LongLogTag")
    private fun executeCommand(commandName: String?, params: Map<*, *>?, callback: ICallbackFromMainprocessToWebViewProcessInterface) {
        mWebViewCommands[commandName]?.execute(params, callback)
    }

}