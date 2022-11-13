package com.oriente.aptsample;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("MainActivity.dispatchTouchEvent---"+getAction(ev)+"---return super.dispatchTouchEvent(ev)");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("MainActivity.onTouchEvent---"+getAction(event)+"---return true");
        return true;
    }

    private String getAction(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            return "Action Down";
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            return "Action Up";
        } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            return "Action Move";
        } else {
            return "Action Down";
        }
    }
}