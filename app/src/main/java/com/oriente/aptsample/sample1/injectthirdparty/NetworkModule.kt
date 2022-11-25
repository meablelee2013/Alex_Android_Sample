package com.oriente.aptsample.sample1.injectthirdparty

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit


@Module
@InstallIn(ActivityComponent::class)
object NetworkModule {

    @Provides
    fun getApiService(): ApiService {
        return Retrofit.Builder().baseUrl("https://www.wanandroid.com").build().create(ApiService::class.java)
    }

    @Provides
//    @Singleton
    fun getOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

}