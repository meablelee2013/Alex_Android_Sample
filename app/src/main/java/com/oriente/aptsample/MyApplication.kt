package com.oriente.aptsample

import android.app.Application
import android.os.Debug
import dagger.hilt.android.HiltAndroidApp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val dateFormat: DateFormat = SimpleDateFormat("dd_MM_yyyy_hh_mm_ss", Locale.getDefault())
        val logDate: String = dateFormat.format(Date())
        // Applies the date and time to the name of the trace log.
        Debug.startMethodTracing("sample-$logDate")
    }
}