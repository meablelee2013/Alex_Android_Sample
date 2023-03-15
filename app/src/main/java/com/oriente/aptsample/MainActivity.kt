package com.oriente.aptsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oriente.aptsample.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    val input = mutableStateOf("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
//                var mainViewModel = viewModel<MainViewModel>()
//                val number = mainViewModel.countDownFlow.collectAsState(initial = 10)
                Column(modifier = Modifier.fillMaxSize()) {
                    HelloContent()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun HelloContent() {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "hello", modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(value = input.value, onValueChange = {
                input.value = it
            })
        }
    }
}