package com.example.common

interface UserAction : BaseAction {

    fun getUserName(): String?
    fun setUserName(userName: String)

}