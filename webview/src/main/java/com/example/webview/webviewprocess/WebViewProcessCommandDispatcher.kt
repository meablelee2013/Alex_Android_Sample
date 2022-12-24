package com.example.webview.webviewprocess

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import com.example.base.loadsir.BaseApplication
import com.example.webview.ICallbackFromMainprocessToWebViewProcessInterface
import com.example.webview.IWebViewProcessToMainProcessInterface
import com.example.webview.mainprocess.MainProcessCommandService

/**
 * 连接主进程的服务 --- Client
 */
object WebViewProcessCommandDispatcher : ServiceConnection {
    private var iWebViewProcessToMainProcessInterface: IWebViewProcessToMainProcessInterface? = null


    fun initAidlConnection() {
        val intent = Intent(BaseApplication.sApplication, MainProcessCommandService::class.java)
        BaseApplication.sApplication?.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    /**
     * 连接成功
     */
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        iWebViewProcessToMainProcessInterface = IWebViewProcessToMainProcessInterface.Stub.asInterface(service)
    }

    /**
     * 断开之后重新连接
     */
    override fun onServiceDisconnected(name: ComponentName?) {
        iWebViewProcessToMainProcessInterface = null
        initAidlConnection()
    }

    /**
     * 死掉之后重新连接
     */
    override fun onBindingDied(name: ComponentName?) {
        iWebViewProcessToMainProcessInterface = null
        initAidlConnection()
    }

    fun executeCommand(commandName: String?, params: String, baseWebView: BaseWebView) {
        iWebViewProcessToMainProcessInterface?.handleWebCommand(commandName,
            params,
            object : ICallbackFromMainprocessToWebViewProcessInterface.Stub() {
                @Throws(RemoteException::class)
                override fun onResult(callbackname: String?, response: String?) {
                    baseWebView.handleCallback(callbackname, response)
                }
            })
    }
}