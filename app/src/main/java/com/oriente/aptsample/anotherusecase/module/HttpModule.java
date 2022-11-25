package com.oriente.aptsample.anotherusecase.module;


import com.oriente.aptsample.anotherusecase.obj.HttpObject;

import dagger.Module;
import dagger.Provides;

@Module
public class HttpModule {

    @Provides  //对外提供或者对外暴露的意思
    public HttpObject getHttpObject(){
        return new HttpObject();
    }
}
