package com.oriente.aptsample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {


    var name: MutableLiveData<String> = MutableLiveData("alex")

    var price: MutableLiveData<Int> = MutableLiveData(119)

    fun next() {

    }
}