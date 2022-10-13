package com.oriente.aptsample

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(var activity: Activity) : ViewModel() {

//    private val _name = MutableLiveData("alex")

    var name: MutableLiveData<String> = MutableLiveData("alex")

    var price: MutableLiveData<Int> = MutableLiveData(119)

    fun next() {
        activity.startActivity(Intent(activity, SecondActivity::class.java))
    }
}