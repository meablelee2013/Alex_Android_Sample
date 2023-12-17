package com.oriente.aptsample

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.common.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.text_dashboard).setOnClickListener {
//            val service = AutoServiceUtil.getService("borrowAction")
//            if (service != null && service is BorrowAction) {
//                service.borrow()
//                service.verifyBorrow()
//            }
//            val userAction = AutoServiceUtil.getService(UserAction::class.java)
//            if (userAction != null && userAction is UserAction) {
//                userAction.getUserName()
//                userAction.setUserName("aaa")
//            }
            var borrowAction = AutoServiceUtil2.getInstance().getService(BorrowAction::class.java)
            borrowAction.borrow()
            borrowAction.verifyBorrow()

            startActivity(Intent(this, SecondActivity::class.java))


        }
    }
}