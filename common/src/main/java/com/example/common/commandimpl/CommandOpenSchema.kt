package com.example.common.commandimpl

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.base.loadsir.BaseApplication
import com.example.base.loadsir.command.Command
import com.google.auto.service.AutoService


@AutoService(Command::class)
class CommandOpenSchema : Command {
    override fun name(): String? {
        return "openSchema"
    }

    override fun execute(params: Map<*, *>) {
        val schema: String = params["schema"].toString()
        Log.d("CommandOpenSchema", schema)
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(schema)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        BaseApplication.sApplication?.startActivity(intent)
    }
}