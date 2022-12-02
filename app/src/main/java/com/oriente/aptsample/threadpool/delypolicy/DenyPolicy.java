package com.oriente.aptsample.threadpool.delypolicy;

public interface DenyPolicy {
    void reject(Runnable runnable);
}
