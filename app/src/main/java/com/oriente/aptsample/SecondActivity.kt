package com.oriente.aptsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.oriente.aptsample.databinding.ActivityMain2Binding

class SecondActivity : AppCompatActivity() {
    @VMScope("count")
    lateinit var viewModel: CountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectViewModel()
        var binding = DataBindingUtil.setContentView<ActivityMain2Binding>(this, R.layout.activity_main2)
        binding.countModel2 = viewModel
        setTitle("secondActivity")

    }
}