package com.oriente.aptsample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MyViewGroup extends LinearLayout {
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        System.out.println("MyViewGroup.dispatchTouchEvent------" + getAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
//        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        System.out.println("MyViewGroup.onInterceptTouchEvent------" + getAction(ev.getAction()));
        return super.onInterceptTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        System.out.println("MyViewGroup.onTouchEvent------" + getAction(ev.getAction()));
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
