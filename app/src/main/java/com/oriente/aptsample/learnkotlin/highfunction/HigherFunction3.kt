package com.oriente.aptsample.learnkotlin.highfunction

fun main() {
    loginEngine1("Alex","123456"){
        if(it) println("登录成功")else println("登录失败")

    }
}
fun loginEngine1(userName: String, userPwd: String,responseResult:(Boolean) ->Unit) {
    if(userName=="Alex" && userPwd=="123456"){
        responseResult(true)
    }else{
        responseResult(false)
    }
}

