package com.example.webview.mainprocess

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * 服务端  给WebViewProcess 提供服务的
 */
class MainProcessCommandService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return MainProcessCommandsManager
    }
}