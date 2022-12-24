package com.example.webview.mainprocess

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * 实现了Stub的主进程需要通过Service对其他进程提供服务
 *
 * 服务端（实现了Stub）需要向客户端（WebViewProcess）公开该接口，以便客户端进行绑定
 */
class MainProcessCommandService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return MainProcessCommandsManager
    }
}