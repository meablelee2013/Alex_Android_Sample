package com.oriente.aptsample.onecase.component;


import com.oriente.aptsample.MainActivity;
import com.oriente.aptsample.onecase.module.ComputerModule;

import dagger.Component;

//快递员

//将电脑注入到使用者的地方
@Component(modules = {ComputerModule.class})
public interface ComputerComponent {
    //用户的收货地址注入进来 依赖快递员把电脑注入到MainActivity
    void injectMainActivity(MainActivity mainActivity);
}
