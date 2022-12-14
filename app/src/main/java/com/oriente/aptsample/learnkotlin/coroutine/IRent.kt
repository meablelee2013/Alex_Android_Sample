package com.oriente.aptsample.learnkotlin.coroutine

interface IRent {

    @GET("/test")
    suspend fun rent(@Field("name") name: String)
}