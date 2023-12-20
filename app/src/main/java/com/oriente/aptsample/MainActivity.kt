package com.oriente.aptsample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.oriente.aptsample.databinding.ActivityMainBinding
import com.oriente.aptsample.viewmodel.UserModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * LiveData + ViewModel + DataBinding + CoroutineScope
 */

class MainActivity : AppCompatActivity() {

    private val userModel by viewModels<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.userModel = userModel
        binding.lifecycleOwner = this

        lifecycleScope.launch {
            repeat(10) {
                delay(2000)
                userModel.age.postValue(userModel.age.value?.plus(100) ?: 10)
                userModel.name.postValue(userModel.name.value + "alex")
            }
        }
    }
}