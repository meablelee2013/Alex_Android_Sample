package com.oriente.aptsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oriente.aptsample.customobject.obj.HttpObject
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    var httpObject: HttpObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        DaggerComputerComponent.builder()
//                .httpModule(new HttpModule())
//                .computerModule(new ComputerModule())
//                .databaseModule(new DatabaseModule())
//                .build().injectMainActivity(this);
        //将需要收货的地址 也就是想使用Computer的地方通过生成的类注入进来
        httpObject!!.sayHttp()
    }
}