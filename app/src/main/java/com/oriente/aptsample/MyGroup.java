package com.oriente.aptsample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyGroup extends LinearLayout {
    public MyGroup(Context context) {
        super(context);
    }

    public MyGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("MyGroup.dispatchTouchEvent---"+getAction(ev)+"--- return super.dispatchTouchEvent(ev)");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("MyGroup.onInterceptTouchEvent---"+getAction(ev)+"---return super.onInterceptTouchEvent(ev)");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("MyGroup.onTouchEvent---"+getAction(event)+"---return super.onTouchEvent(event)");
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
