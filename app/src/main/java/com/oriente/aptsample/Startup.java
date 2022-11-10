package com.oriente.aptsample;

import android.content.Context;

import java.util.List;

public interface Startup<T> {

    T create(Context context);

    /**
     *
     * @return
     */
    List<Class<? extends Startup<?>>> dependencies();

    int getDependenciesCount();
}
