package com.example.webview.mainprocess

import android.annotation.SuppressLint
import com.example.base.loadsir.command.Command
import com.example.webview.IWebviewProcessToMainProcessInterface
import com.google.gson.Gson
import java.util.*

const val TAG = "MainProcessCommandsManager"

object MainProcessCommandsManager : IWebviewProcessToMainProcessInterface.Stub() {
    private val mCommands = HashMap<String, Command>()

    init {
        val serviceLoader: ServiceLoader<Command> = ServiceLoader.load(Command::class.java)
        for (command in serviceLoader) {
            if (!mCommands.containsKey(command.name())) {
                mCommands[command.name()!!] = command
            }
        }
    }

    override fun handleWebCommand(commandName: String?, jsonParmas: String?) {
        val fromJson = Gson().fromJson<Map<*, *>>(jsonParmas, MutableMap::class.java)
        if (fromJson is Map<*, *>) {
            executeCommand(commandName, fromJson as Map<String, Any>)
        }
    }

    @SuppressLint("LongLogTag")
    private fun executeCommand(commandName: String?, params: Map<String, Any>) {
        mCommands[commandName]?.execute(params)
    }

}