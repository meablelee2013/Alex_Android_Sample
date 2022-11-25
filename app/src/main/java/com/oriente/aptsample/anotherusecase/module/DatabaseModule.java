package com.oriente.aptsample.anotherusecase.module;


import com.oriente.aptsample.anotherusecase.obj.DatabaseObject;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    public DatabaseObject getDatabase() {
        return new DatabaseObject();
    }
}
