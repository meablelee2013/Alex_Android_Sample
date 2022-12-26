package com.oriente.aptsample.customobject.component;

import com.oriente.aptsample.customobject.module.DatabaseModule;
import com.oriente.aptsample.customobject.module.HttpModule;

import dagger.Component;

@Component(modules = {DatabaseModule.class, HttpModule.class})
public interface MyComponent {

    void injectMainActivity2(MainActivity mainActivity);
}
