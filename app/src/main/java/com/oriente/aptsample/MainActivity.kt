package com.oriente.aptsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    @Inject
//    lateinit var analyticsAdapter: AnalyticsAdapter

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var testInterface: TestInterface


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        analyticsAdapter.analytics()

        testInterface.doSomeThing()

        Log.d("alex", "" + okHttpClient.callTimeoutMillis)


    }
}