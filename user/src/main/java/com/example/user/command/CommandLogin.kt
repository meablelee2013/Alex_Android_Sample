package com.example.user.command

import android.util.Log
import com.example.common.AutoServiceManager
import com.example.common.autoservice.IUserCenterService
import com.example.common.eventbus.LoginEvent
import com.example.webview.ICallbackFromMainprocessToWebViewProcessInterface
import com.google.auto.service.AutoService
import com.google.gson.Gson
import command.WebViewCommand
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

@AutoService(WebViewCommand::class)
class CommandLogin : WebViewCommand {
    private val iUserCenterService: IUserCenterService?
    private var callbacknameFromNativeJs: String? = null
    private var callback: ICallbackFromMainprocessToWebViewProcessInterface? = null

    init {
        EventBus.getDefault().register(this)
        iUserCenterService = AutoServiceManager.load(IUserCenterService::class.java)
    }

    override fun name(): String? {
        return "login"
    }

    override fun execute(params: Map<*, *>, callback: ICallbackFromMainprocessToWebViewProcessInterface) {
        Log.d("CommandLogin", params.toString())
        if (iUserCenterService != null && !iUserCenterService.isLogin()) {
            iUserCenterService?.login()
            this.callback = callback
            this.callbacknameFromNativeJs = params["callbackname"].toString()
        }
    }

    @Subscribe
    fun onMessageEvent(event: LoginEvent) {
        Log.e("CommandLogin", event.toString())
        val map: HashMap<String, String> = HashMap()
        map["accountName"] = event.userName
        callback?.onResult(callbacknameFromNativeJs, Gson().toJson(map))
    }
}