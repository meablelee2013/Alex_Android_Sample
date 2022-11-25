package com.oriente.aptsample.sample1.injectinterface

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


/**
 * 新建一个抽你类，方法也必须是一个抽象方法，抽象类使用@Module注解，方法使用@Bind注解，方法参数为接口的实现，方法的返回值为需要绑定的接口
 */
@Module
@InstallIn(ActivityComponent::class)
abstract class TestModule {


    @Binds //注入实现类与接口   参数是实现类，返回接口
    abstract fun bindTestInterface(testImpl: TestInterfaceImpl): TestInterface


}