package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("MyService.onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("MyService.onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("MyService.onDestroy");
    }

    //服务端将服务暴露出去，
    class MyBind extends IMyAidlInterface.Stub {

        @Override
        public String getString() throws RemoteException {
            return "hello world";
        }
    }
}
