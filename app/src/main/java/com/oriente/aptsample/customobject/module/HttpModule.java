package com.oriente.aptsample.customobject.module;


import com.oriente.aptsample.customobject.obj.HttpObject;

import dagger.Module;
import dagger.Provides;

@Module
public class HttpModule {

    @Provides  //对外提供或者对外暴露的意思
    public HttpObject getHttpObject(){
        return new HttpObject();
    }
}
