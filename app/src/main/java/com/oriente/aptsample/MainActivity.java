package com.oriente.aptsample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.oriente.aptsample.anotherusecase.obj.HttpObject;
import com.oriente.aptsample.onecase.obj.Computer;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {


    @Inject
    Computer computer;

    @Inject
    HttpObject httpObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DaggerComputerComponent.builder()
//                .httpModule(new HttpModule())
//                .computerModule(new ComputerModule())
//                .databaseModule(new DatabaseModule())
//                .build().injectMainActivity(this);
        //将需要收货的地址 也就是想使用Computer的地方通过生成的类注入进来
        computer.compute();
        httpObject.sayHttp();

//

    }
}