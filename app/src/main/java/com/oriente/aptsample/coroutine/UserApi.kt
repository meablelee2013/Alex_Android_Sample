package com.oriente.aptsample.coroutine

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class User(val name: String, val address: String) {}


val userServiceApi: UserServiceApi by lazy {
    val retrofit = retrofit2.Retrofit.Builder()
        .client(OkHttpClient().newBuilder().addInterceptor() {
        it.proceed(it.request()).apply {
            Log.d("alex", "request:${code()}")
        }
    }.build())
        .baseUrl("http://")//mock
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    retrofit.create(UserServiceApi::class.java)

}

interface UserServiceApi {

    @GET("user")
    fun loadUser(@Query("name") name: String): Call<User>

    @GET("user")
    suspend fun getUser(@Query("name") name: String): User

}