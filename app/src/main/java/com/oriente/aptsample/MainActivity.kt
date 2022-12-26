package com.oriente.aptsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oriente.aptsample.customobject.component.DaggerMyComponent
import com.oriente.aptsample.customobject.obj.HttpObject
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @set:Inject
    var httpObject: HttpObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //第一种方式
        DaggerMyComponent.create().injectMainActivity2(this)
        //第二种方式
//        DaggerComputerComponent.builder()
//                .httpModule(new HttpModule())
//                .computerModule(new ComputerModule())
//                .databaseModule(new DatabaseModule())
//                .build().injectMainActivity(this);
        //将需要收货的地址 也就是想使用Computer的地方通过生成的类注入进来
        httpObject!!.sayHttp()
    }
}