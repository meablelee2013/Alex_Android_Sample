package com.oriente.aptsample.sample2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.oriente.aptsample.MyApplication
import com.oriente.aptsample.R
import com.oriente.aptsample.databinding.ActivitySecondBinding
import com.oriente.aptsample.sample2.app.annotation.BindOkHttp
import com.oriente.aptsample.sample2.bean.ResponseData
import com.oriente.aptsample.sample2.network.callback.HttpCallback
import com.oriente.aptsample.sample2.network.http.IHttpRequest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {
    var mBinding: ActivitySecondBinding? = null

    @BindOkHttp
    @Inject
    lateinit var iHttpRequest: IHttpRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_second)
        mBinding?.checkNetWork?.setOnClickListener {
            // 公网地址

            // 公网地址
            val url = "https://autodev.openspeech.cn/csp/api/v2.1/weather?openId=aiuicus&clientType=android&sign=android&city=%E4%B8%8A%E6%B5%B7&needMoreData=true&pageNo=1&pageSize=7%20%E2%80%94%E2%80%94%E2%80%94%E2%80%94%E2%80%94%E2%80%94%E2%80%94%E2%80%94%E2%80%94%E2%80%94%E2%80%94%E2%80%94%E2%80%94%E2%80%94%E2%80%94%E2%80%94%20%E7%89%88%E6%9D%83%E5%A3%B0%E6%98%8E%EF%BC%9A%E6%9C%AC%E6%96%87%E4%B8%BACSDN%E5%8D%9A%E4%B8%BB%E3%80%8C%E6%BD%87%E6%BD%87%E7%8B%AC%E8%A1%8C%E4%BE%A0%E3%80%8D%E7%9A%84%E5%8E%9F%E5%88%9B%E6%96%87%E7%AB%A0%EF%BC%8C%E9%81%B5%E5%BE%AACC%204.0%20BY-SA%E7%89%88%E6%9D%83%E5%8D%8F%E8%AE%AE%EF%BC%8C%E8%BD%AC%E8%BD%BD%E8%AF%B7%E9%99%84%E4%B8%8A%E5%8E%9F%E6%96%87%E5%87%BA%E5%A4%84%E9%93%BE%E6%8E%A5%E5%8F%8A%E6%9C%AC%E5%A3%B0%E6%98%8E%E3%80%82%20%E5%8E%9F%E6%96%87%E9%93%BE%E6%8E%A5%EF%BC%9Ahttps://blog.csdn.net/qq_25269161/article/details/125188009"
//            val url = "https://v.juhe.cn/historyWeather/citys"
            val params = HashMap<String, Any>()
            // https://v.juhe.cn/historyWeather/citys?&province_id=2&key=bb52107206585ab074f5e59a8c73875b
            // https://v.juhe.cn/historyWeather/citys?&province_id=2&key=bb52107206585ab074f5e59a8c73875b
//            params["province_id"] = "2"
//            params["key"] = "bb52107206585ab074f5e59a8c73875b"

            iHttpRequest?.get(url, object : HttpCallback<ResponseData?>() {
                override fun onSuccess(objResult: ResponseData?) {
                    Toast.makeText(this@SecondActivity, objResult.toString(), Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}