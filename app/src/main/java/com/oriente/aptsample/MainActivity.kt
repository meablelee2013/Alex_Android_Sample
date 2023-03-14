package com.oriente.aptsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oriente.aptsample.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                var mainViewModel = viewModel<MainViewModel>()
                val number = mainViewModel.countDownFlow.collectAsState(initial = 10)
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = number.value.toString(), fontSize = 30.sp, modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}