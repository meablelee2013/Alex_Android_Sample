package com.oriente.aptsample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserModel : ViewModel() {
    private var _name = MutableLiveData("Ada")
    private var _lastName = MutableLiveData("Lovelace")
    private var _age =
        MutableLiveData(1)//不是string的字段，在xml里要toString(),否则会crash      android:text="@{userModel.age.toString()}"

    val name: MutableLiveData<String> = _name
    val lastName: MutableLiveData<String> = _lastName
    val age: MutableLiveData<Int> = _age
}