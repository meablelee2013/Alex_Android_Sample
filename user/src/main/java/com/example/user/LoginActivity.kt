package com.example.user

import android.app.ProgressDialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.common.eventbus.LoginEvent
import com.google.android.material.textfield.TextInputLayout
import org.greenrobot.eventbus.EventBus

class LoginActivity : AppCompatActivity() {
    private var btnLogin: Button? = null
    private var btnLinkToRegister: Button? = null
    private var btnForgotPass: Button? = null
    private var inputEmail: TextInputLayout? = null
    private var inputPassword: TextInputLayout? = null
    private var pDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        inputEmail = findViewById(R.id.lTextEmail)
        inputPassword = findViewById(R.id.lTextPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnLinkToRegister = findViewById(R.id.btnLinkToRegisterScreen)
        btnForgotPass = findViewById(R.id.btnForgotPassword)

        // Progress dialog
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)

        // Hide Keyboard
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        init()
    }

    private fun init() {
        // Login button Click Event
        btnLogin!!.setOnClickListener {
            EventBus.getDefault().post(LoginEvent(inputEmail!!.editText!!.text.toString()))
            finish()
        }
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
        private const val KEY_UID = "uid"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_CREATED_AT = "created_at"
    }
}
