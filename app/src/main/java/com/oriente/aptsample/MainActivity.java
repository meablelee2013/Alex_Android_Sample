package com.oriente.aptsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("MainActivity.onCreate");
        findViewById(R.id.myView).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MainActivity2.class));
        });
    }

    @Override
    protected void onResume() {
        System.out.println("MainActivity.onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        System.out.println("MainActivity.onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        System.out.println("MainActivity.onRestart");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        System.out.println("MainActivity.onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        System.out.println("MainActivity.onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("MainActivity.onDestroy");
        super.onDestroy();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

//        System.out.println("MainActivity.dispatchTouchEvent------" + getAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        System.out.println("MainActivity.onTouchEvent------" + getAction(ev.getAction()));
        return super.onTouchEvent(ev);
    }

    public String getAction(int action) {
        if (action == MotionEvent.ACTION_DOWN) {
            return "ACTION_DOWN";
        } else if (action == MotionEvent.ACTION_UP) {
            return "ACTION_UP";
        } else if (action == MotionEvent.ACTION_MOVE) {
            return "ACTION_MOVE";
        } else {
            return "ACTION_DOWN";
        }
    }
}