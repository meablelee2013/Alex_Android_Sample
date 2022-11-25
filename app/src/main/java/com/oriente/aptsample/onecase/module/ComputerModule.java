package com.oriente.aptsample.onecase.module;

import com.oriente.aptsample.onecase.obj.Computer;

import dagger.Module;
import dagger.Provides;

//打包员   还是apt技术 arouter databinding room 都是apt编译期生成

//生成电脑的地方
@Module
public class ComputerModule {

    //暴露我们的电脑
    @Provides
    public Computer getComputer(){
        return new Computer();
    }
}
