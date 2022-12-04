package com.oriente.aptsample

import android.os.Bundle
import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test(ALEX)

    }

    companion object {
        const val ALEX = 0
        const val LEI = 1
    }

    @IntDef(value = [ALEX, LEI])
    @Retention(AnnotationRetention.SOURCE)
    @Target(AnnotationTarget.VALUE_PARAMETER)
    annotation class Teacher()

    fun test(@Teacher teacher: Int) {}
}