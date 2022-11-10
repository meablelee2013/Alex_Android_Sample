package com.example.service;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Handle the splash screen transition.
        setContentView(R.layout.activity_main);

//        findViewById(R.id.text).setOnClickListener(v -> {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Looper.prepare();
//                    mHandler = new Handler() {
//                        @Override
//                        public void handleMessage(@NonNull Message msg) {
//                            super.handleMessage(msg);
//                        }
//                    };
//                    Looper.loop();
//                }
//            }).start();
//        });


    }
}