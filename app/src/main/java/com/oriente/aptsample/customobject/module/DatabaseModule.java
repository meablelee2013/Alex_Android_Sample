package com.oriente.aptsample.customobject.module;


import com.oriente.aptsample.customobject.obj.DatabaseObject;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    public DatabaseObject getDatabase() {
        return new DatabaseObject();
    }
}
