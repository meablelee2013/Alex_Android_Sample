package com.oriente.aptsample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyView extends androidx.appcompat.widget.AppCompatButton {
    public MyView(@NonNull Context context) {
        super(context);
    }

    public MyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        System.out.println("MyView.dispatchTouchEvent------" + getAction(event.getAction()));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        System.out.println("MyView.onTouchEvent------" + getAction(event.getAction()));
        return super.onTouchEvent(event);
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
