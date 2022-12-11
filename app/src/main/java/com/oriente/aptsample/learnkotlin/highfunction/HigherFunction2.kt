package com.oriente.aptsample.learnkotlin.highfunction

fun loginEngine(userName: String, userPwd: String) {
    loginCheck(userName, userPwd) { name, pwd ->
        if (name == "Alex" && pwd == "123456") {
            println("恭喜 $name 登录成功")
        } else {
            println("对不起 $name 不正确，登录失败")
        }
    }

}

fun loginCheck(userName: String, userPwd: String, checkResult: (String, String) -> Unit) {
    if (userName.isEmpty() || userPwd.isEmpty()) {
        return
    }
    //做了一系列较验工作
    //检查通过
    checkResult(userName, userPwd)
}

fun main() {
    loginEngine("Alex", "123456")
}