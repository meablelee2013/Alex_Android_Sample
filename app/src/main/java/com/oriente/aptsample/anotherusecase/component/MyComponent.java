package com.oriente.aptsample.anotherusecase.component;

import com.oriente.aptsample.MainActivity;
import com.oriente.aptsample.anotherusecase.module.DatabaseModule;
import com.oriente.aptsample.anotherusecase.module.HttpModule;

import dagger.Component;

@Component(modules = {DatabaseModule.class, HttpModule.class})
public interface MyComponent {

    void injectMainActivity2(MainActivity mainActivity);
}
