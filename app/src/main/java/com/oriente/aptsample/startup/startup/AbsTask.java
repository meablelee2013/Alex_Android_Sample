package com.oriente.aptsample.startup.startup;

import android.content.Context;

import java.util.List;

public interface AbsTask<T> extends Dispatcher {

    T create(Context context);

    /**
     * 本任务依赖哪些任务
     *
     * @return
     */
    List<Class<? extends AbsTask<?>>> dependencies();


    int getDependenciesCount();
}
