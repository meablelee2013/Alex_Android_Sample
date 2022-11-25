package com.oriente.aptsample.sample1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.oriente.aptsample.R
import com.oriente.aptsample.sample1.injectcustomobjectwithprovides.Computer
import com.oriente.aptsample.sample1.injectinterface.TestInterface
import com.oriente.aptsample.sample1.injectcustomobjectwithinject.AnalyticsAdapter
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mComputer: Computer

    //注入自己的实现类
    @Inject
    lateinit var mAnalyticsAdapter: AnalyticsAdapter

    //    注入第三方类库
    @Inject
    lateinit var mOkHttpClient: OkHttpClient

    //注入接口
    @Inject
    lateinit var mTestInterface: TestInterface


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mComputer.sayHello()

        mAnalyticsAdapter.analytics()

        mTestInterface.doSomeThing()

        Log.d("alex", "" + mOkHttpClient.callTimeoutMillis)


    }
}