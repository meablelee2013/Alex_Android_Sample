package com.example.base.loadsir.command

interface Command {

    fun name(): String?
    fun execute(params: Map<*, *>)

}