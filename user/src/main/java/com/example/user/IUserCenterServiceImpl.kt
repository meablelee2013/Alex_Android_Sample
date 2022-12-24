package com.example.user

import android.content.Intent
import com.example.base.loadsir.BaseApplication
import com.example.common.autoservice.IUserCenterService
import com.google.auto.service.AutoService

@AutoService(IUserCenterService::class)
class IUserCenterServiceImpl : IUserCenterService {
    override fun isLogin(): Boolean {
        return false
    }

    override fun login() {
        val intent = Intent(BaseApplication.sApplication, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        BaseApplication.sApplication?.startActivity(intent)
    }
}