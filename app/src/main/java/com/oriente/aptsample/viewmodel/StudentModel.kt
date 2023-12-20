package com.oriente.aptsample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class StudentModel(application: Application) : AndroidViewModel(application) {

    val name by lazy { MutableLiveData<String>() }

    init {
        name.value = ""
    }
}