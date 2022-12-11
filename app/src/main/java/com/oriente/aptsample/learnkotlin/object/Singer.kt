package com.oriente.aptsample.learnkotlin.`object`

interface Sing{
    fun sing()
}

open class Singer :Sing{
    override fun sing() {

    }
}

class Decorator(var singer:Singer) :Sing{
    override fun sing() {
        println("pre sign")
        singer.sing()
        println("after sign")
    }
    fun dance(){

    }
}

class EnhanceSinger :Singer(){
    override fun sing() {
        super.sing()
    }
    //增加的功能
    fun dance(){

    }

}