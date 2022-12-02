package com.oriente.aptsample

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.oriente.aptsample.databinding.ActivityMainBinding
import kotlin.concurrent.thread

const val TAG = "alex"

class MainActivity1 : AppCompatActivity() {

    @VMScope("count")
    lateinit var viewModel: CountViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectViewModel()
        var binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.countModel = viewModel
        setTitle("firstActivity")

         findViewById<Button>(R.id.button).setOnClickListener {
            println("before count=" + viewModel.count.value)
            viewModel.count.value = viewModel.count.value?.plus(2)
            println("after count=" + viewModel.count.value)

            startActivity(Intent(this@MainActivity1, SecondActivity::class.java))


        }


        val countViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(CountViewModel::class.java)
    }


//    private fun testAnr() {
//
//        val lock1 = Object()
//        val lock2 = Object()
//
//        //子线程持有锁1，想要竞争锁2
//        thread {
//            synchronized(lock1) {
//                Thread.sleep(100)
//
//                synchronized(lock2) {
//                    Log.d(TAG, "testAnr: getLock2")
//                }
//            }
//        }
//
//        //主线程持有锁2，想要竞争锁1
//        synchronized(lock2) {
//            Thread.sleep(100)
//
//            synchronized(lock1) {
//                Log.d(TAG, "testAnr: getLock1")
//            }
//        }
//    }
}