package com.example.webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.webview.databinding.ActivityWebviewBinding
import com.example.webview.utils.TITLE
import com.example.webview.utils.URL

class WebViewActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityWebviewBinding
    private lateinit var fragment: WebViewFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_webview)
        mBinding.title.text = intent?.getStringExtra(TITLE)

        fragment = WebViewFragment.newInstance(intent.getStringExtra(URL), true)!!
        val beginTransaction = supportFragmentManager.beginTransaction()
        if (fragment != null) {
            beginTransaction.replace(R.id.web_view_fragment, fragment).commit()
        }

        mBinding.back.setOnClickListener {
            fragment.goBack()
        }

    }

    fun updateTitle(title: String?) {
        mBinding.title.text = title
    }
}