package com.oriente.aptsample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("MyView.dispatchTouchEvent---" + getAction(event)+"---return super.dispatchTouchEvent(event)");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("MyView.onTouchEvent---" + getAction(event)+"---return super.onTouchEvent(event)");
        return super.onTouchEvent(event);
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
