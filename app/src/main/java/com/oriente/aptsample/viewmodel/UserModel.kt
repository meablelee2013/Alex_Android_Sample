package com.oriente.aptsample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class UserModel(application: Application) : AndroidViewModel(application) {
    var name = MutableLiveData("Ada")
    private var _lastName = MutableLiveData("Lovelace")
    var age =
        MutableLiveData(1)//不是string的字段，在xml里要toString(),否则会crash      android:text="@{userModel.age.toString()}"

}