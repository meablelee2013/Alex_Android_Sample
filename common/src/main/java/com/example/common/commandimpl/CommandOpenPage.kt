package com.example.common.commandimpl

import android.content.ComponentName
import android.content.Intent
import android.text.TextUtils
import com.example.base.loadsir.BaseApplication
import com.example.base.loadsir.command.Command
import com.google.auto.service.AutoService

@AutoService(Command::class)
class CommandOpenPage : Command {
    override fun name(): String? {
        return "openPage"
    }

    override fun execute(params: Map<*, *>) {
        val targetClass: String = params["target_class"].toString()
        if (!TextUtils.isEmpty(targetClass)) {
            val intent = Intent()
            intent.component = BaseApplication.sApplication?.let { ComponentName(it, targetClass) }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            BaseApplication.sApplication?.startActivity(intent)
        }
    }
}