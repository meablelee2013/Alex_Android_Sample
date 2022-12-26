package com.oriente.aptsample.sample1.injectcustomobjectwithprovides

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
class ComputerModule {

    @Provides
    fun getComputer(): Computer {
        return Computer()
    }
}