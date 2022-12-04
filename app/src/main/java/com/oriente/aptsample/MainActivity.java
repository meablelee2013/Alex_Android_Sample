package com.oriente.aptsample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.annotation.TimeCheck;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @TimeCheck
    public void test() {
        System.out.println("test");
    }
}