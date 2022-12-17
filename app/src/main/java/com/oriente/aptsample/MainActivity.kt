package com.oriente.aptsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.common.AutoServiceUtil
import com.example.common.BorrowAction
import com.example.common.UserAction

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val service = AutoServiceUtil.getService("borrowAction")
        if (service != null && service is BorrowAction) {
            service.borrow()
            service.verifyBorrow()
        }
        val userAction = AutoServiceUtil.getService(UserAction::class.java)
        if (userAction != null && userAction is UserAction) {
            userAction.getUserName()
            userAction.setUserName("aaa")
        }

    }
}