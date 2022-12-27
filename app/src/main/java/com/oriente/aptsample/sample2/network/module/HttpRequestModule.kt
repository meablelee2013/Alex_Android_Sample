package com.oriente.aptsample.sample2.network.module

import com.oriente.aptsample.sample2.app.annotation.BindOkHttp
import com.oriente.aptsample.sample2.app.annotation.BindVolley
import com.oriente.aptsample.sample2.app.annotation.BindXUtils
import com.oriente.aptsample.sample2.network.OkHttpRequest
import com.oriente.aptsample.sample2.network.VolleyRequest
import com.oriente.aptsample.sample2.network.XUtilsRequest
import com.oriente.aptsample.sample2.network.http.IHttpRequest
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
open abstract class HttpRequestModule {

    @BindOkHttp
    @Binds
    @Singleton
    abstract fun bindOkHttp(okHttpRequest: OkHttpRequest): IHttpRequest

    //       接口                实现类      绑定关系
    @BindVolley
    @Binds
    @Singleton
    abstract fun bindVolley(volleyRequest: VolleyRequest): IHttpRequest

    //       接口                实现类      绑定关系
    @BindXUtils
    @Binds
    @Singleton
    abstract fun bindXUtils(xUtilsRequest: XUtilsRequest): IHttpRequest
}