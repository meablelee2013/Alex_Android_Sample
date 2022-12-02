package com.oriente.aptsample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountViewModel : ViewModel() {
    val count: MutableLiveData<Int> = MutableLiveData(3)

}