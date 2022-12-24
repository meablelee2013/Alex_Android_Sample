package com.example.webview.mainprocess

import android.annotation.SuppressLint
import command.WebViewCommand
import com.example.webview.IWebviewProcessToMainProcessInterface
import com.example.webview.ICallbackFromMainprocessToWebViewProcessInterface
import com.google.gson.Gson
import java.util.*

const val TAG = "MainProcessCommandsManager"

object MainProcessCommandsManager : IWebviewProcessToMainProcessInterface.Stub() {
    private val mCommands = HashMap<String, WebViewCommand>()

    init {
        val serviceLoader: ServiceLoader<WebViewCommand> = ServiceLoader.load(WebViewCommand::class.java)
        for (command in serviceLoader) {
            if (!mCommands.containsKey(command.name())) {
                mCommands[command.name()!!] = command
            }
        }
    }

    override fun handleWebCommand(commandName: String?, jsonParmas: String?, callback: ICallbackFromMainprocessToWebViewProcessInterface) {
        val fromJson = Gson().fromJson<Map<*, *>>(jsonParmas, MutableMap::class.java)
        if (fromJson is Map<*, *>) {
            executeCommand(commandName, fromJson as Map<String, Any>, callback)
        }
    }

    @SuppressLint("LongLogTag")
    private fun executeCommand(commandName: String?, params: Map<String, Any>, callback: ICallbackFromMainprocessToWebViewProcessInterface) {
        mCommands[commandName]?.execute(params, callback)
    }

}