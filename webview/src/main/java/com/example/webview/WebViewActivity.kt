package com.example.webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.webview.databinding.ActivityWebviewBinding
import com.example.webview.utils.TITLE
import com.example.webview.utils.URL

class WebViewActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_webview)
        mBinding.title.text = intent?.getStringExtra(TITLE)

        val fragment = WebViewFragment.BaseWebViewFragment.newInstance(intent.getStringExtra(URL), true)
        val beginTransaction = supportFragmentManager.beginTransaction()

        mBinding.back.setOnClickListener {
            fragment?.goBack()
        }
        if (fragment != null) {
            beginTransaction.replace(R.id.web_view_fragment, fragment).commit()
        }
    }

    fun updateTitle(title: String?) {
        mBinding.title.text = title
    }
}