package com.example.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.base.loadsir.loadsir.ErrorCallback
import com.example.base.loadsir.loadsir.LoadingCallback
import com.example.webview.databinding.FragmentWebviewBinding
import com.example.webview.utils.CAN_NATIVE_REFRESH
import com.example.webview.utils.URL
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

class WebViewFragment : Fragment(), WebViewCallBack, OnRefreshListener {
    private var mUrl: String? = null
    private var mCanNativeRefresh: Boolean? = null
    private lateinit var mBinding: FragmentWebviewBinding
    private var mLoadService: LoadService<*>? = null
    private var mIsError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUrl = arguments?.getString(URL)
        mCanNativeRefresh = arguments?.getBoolean(CAN_NATIVE_REFRESH)
    }

    companion object {
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
        mBinding.webview.registerWebViewCallBack(this)
        mUrl?.let { mBinding.webview.loadUrl(it) }

        mLoadService = LoadSir.getDefault().register(mBinding.smartrefreshlayout) {
            mLoadService?.showCallback(LoadingCallback::class.java)
            mBinding.webview.reload()
        }

        mBinding.smartrefreshlayout.setOnRefreshListener(this)
        mCanNativeRefresh?.let { mBinding.smartrefreshlayout.setEnableRefresh(it) }
        mBinding.smartrefreshlayout.setEnableLoadMore(false)
        return mLoadService?.loadLayout
    }


    override fun onPageStarted(url: String?) {
        mLoadService?.showCallback(LoadingCallback::class.java)
    }

    override fun onPageFinished(url: String?) {
        if (mIsError) {
            mBinding.smartrefreshlayout.setEnableRefresh(true)
        } else {
            mCanNativeRefresh?.let { mBinding.smartrefreshlayout.setEnableRefresh(it) }
        }
        mBinding.smartrefreshlayout.finishRefresh()
        if (mIsError) {
            mLoadService?.showCallback(ErrorCallback::class.java)
        } else {
            mLoadService?.showSuccess()
        }
        mIsError = false
    }

    override fun onError() {
        mIsError = true
        mBinding.smartrefreshlayout.finishRefresh()
    }

    override fun onReceivedTitle(title: String?) {
        var activtiy = requireActivity()
        if (activtiy is WebViewActivity) {
            activtiy.updateTitle(title)
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mBinding.webview.reload()
    }

    fun goBack() {
        if (mBinding.webview.canGoBack()) {
            mBinding.webview.goBack()
        } else {
            requireActivity().finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.webview.unBind()
    }

}