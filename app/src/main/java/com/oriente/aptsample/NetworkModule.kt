package com.oriente.aptsample

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun getApiService(): ApiService {
        return Retrofit.Builder().baseUrl("https://www.wanandroid.com")
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun getOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

}