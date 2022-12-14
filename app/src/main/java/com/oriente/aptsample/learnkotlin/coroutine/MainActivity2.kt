package com.oriente.aptsample.learnkotlin.coroutine

import android.annotation.SuppressLint
import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.AsyncTaskLoader
import com.oriente.aptsample.R
import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class MainActivity2 : AppCompatActivity() {

    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        test()
//        println("alex outside test " + System.currentTimeMillis())
//        var tvState21: TextView = findViewById<TextView>(R.id.tvState21)
        var btn21: Button = findViewById<Button>(R.id.btn21).also {
            it.setOnClickListener {

                Thread.sleep(12000)
                Log.d("alex", "${Thread.currentThread().name}: after sleep")
//                //这是采用异步线程池的方式访问，做任务和任务结果是在2个方法里
//                object : AsyncTask<Void, Void, User?>() {
//                    override fun doInBackground(vararg params: Void?): User? {
//                        //去做一些耗时任务
//                        return userServiceApi.loadUser("haha").execute().body()
//                    }
//
//                    override fun onPostExecute(result: User?) {
//                        super.onPostExecute(result)
//                        result?.let {
//                            tvState21.text = "name is ${it.name} and address is ${it.address}"
//                        }//接口请求回来的结果
//                    }
//
//                }
//                //这是采用协程的方式  更新UI在主线程
//                GlobalScope.launch(Dispatchers.Main) {
//                    val user: User = withContext(Dispatchers.IO) {
//                        //做一些耗时任务
//                        userServiceApi.getUser("haha")
//                    }
//                    //拿到耗时任务返回的结果更新ui
//                    tvState21.text = "name is ${user.name} and address is ${user.address}" //接口请求回来的结果
//                }
            }
        }


    }

    private fun test() {

        //launch 是同步按顺序执行
        GlobalScope.launch(Dispatchers.Main) {
//            println("before withContext currentThread() id is ${Thread.currentThread().id}")
            println("alex before testSuspend " + System.currentTimeMillis())
            testSuspend()
//            withContext(Dispatchers.IO){
//                println("with in withContext currentThread() id is ${Thread.currentThread().id}")
//            }
            println("alex after testSuspend " + System.currentTimeMillis())
        }
//        //async 异步执行
//        GlobalScope.async {
//            println("with in async  currentThread() id is ${Thread.currentThread().id}")
//        }
    }

    /**
     * 如果在被suspend修饰的方法里没有withContext，编译器会报这个suspend是多余的
     */
    private suspend fun testSuspend() {
        println("alex in testSuspend before" + System.currentTimeMillis())
        withContext(Dispatchers.IO) {
            Thread.sleep(5000)
            println("alex with in withContext currentThread() id is ${Thread.currentThread().id}")
        }
        println("alex in testSuspend after" + System.currentTimeMillis())

    }

    var method02 = { str: String -> true }

}

