package com.example.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.webview.databinding.FragmentWebviewBinding
import com.example.webview.utils.CAN_NATIVE_REFRESH
import com.example.webview.utils.URL

class WebViewFragment : Fragment() {
    private var mUrl: String? = null
    lateinit var mBinding: FragmentWebviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUrl = arguments?.getString(URL)
    }

    object BaseWebViewFragment {
        fun newInstance(url: String?, canNativeRefresh: Boolean): WebViewFragment? {
            val fragment = WebViewFragment()
            val bundle = Bundle()
            bundle.putString(URL, url)
            bundle.putBoolean(CAN_NATIVE_REFRESH, canNativeRefresh)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_webview, container, false)
//        mUrl?.let { mBinding.webview.loadUrl(it) }
        mBinding.webview.loadUrl("https://www.baidu.com")
        return mBinding.root
    }
}