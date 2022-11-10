package com.oriente.aptsample;

import java.util.List;

public abstract class AndroidStartup<T> implements Startup<T> {

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return null;
    }

    /**
     * 入度数
     * @return
     */
    @Override
    public int getDependenciesCount() {
        List<Class<? extends Startup<?>>> dependencies = dependencies();
        return dependencies == null ? 0 : dependencies.size();
    }
}
