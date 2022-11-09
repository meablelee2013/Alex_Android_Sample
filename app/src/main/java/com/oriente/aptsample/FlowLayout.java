package com.oriente.aptsample;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private static final int HORIZONTAL_SPACE = DensityUtil.dip2px(16);
    private static final int VERTICAL_SPACE = DensityUtil.dip2px(8);

    //代码new的时候
    public FlowLayout(Context context) {
        super(context);
    }

    //反射
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //主题style
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * MeasureSpec 32位的int值，高2位是Mode，测量模式，低30位是具体测量大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("alex", "onMeasure");

        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);//viewGroup的宽度
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);//viewGroup的高度

        List<View> lineViews = new ArrayList<>();//保存一行中所有的view
        int lineWidthUsed = 0;//记录这行已经使用了多宽的size
        int lineHeight = 0;//一行的行高

        int parentNeedWidth = 0;
        int parentNeedHeight = 0;
        //度量子View大小
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //当前子View的LayoutParams
            LayoutParams childLP = childView.getLayoutParams();
            /**
             * 3个参数
             * 第一个 父亲的widthMeasureSpec或者heightMeasureSpec
             * 第二个 父亲的padding，父亲的padding是子view不能越过的区域
             * 第三个 子View的宽或者高
             */
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childLP.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, childLP.height);
            //measure方法中会再次调用onMeasure，形成递归的计算childView(如果是ViewGroup)的子View的大小
            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            int childMeasuredWidth = childView.getMeasuredWidth();
            int childMeasuredHeight = childView.getMeasuredHeight();

            //当前行已经使用的宽度+当前孩子的宽度 如果大于父ViewGroup的宽度，则需要换行
            if (lineWidthUsed + childMeasuredWidth > selfWidth) {
                //一旦换行了，需要判断当前的宽和高
                parentNeedWidth = Math.max(parentNeedWidth, lineWidthUsed + HORIZONTAL_SPACE);
                parentNeedHeight = parentNeedHeight + lineHeight + VERTICAL_SPACE;

                lineViews = new ArrayList<>();
                lineWidthUsed = 0;
                lineHeight = 0;
            }
            lineViews.add(childView);
            lineWidthUsed = lineWidthUsed + childMeasuredWidth + HORIZONTAL_SPACE;
            lineHeight = Math.max(lineHeight, childMeasuredHeight);

            //处理最后一行数据
            if (i == childCount - 1) {
                parentNeedWidth = Math.max(parentNeedWidth, lineWidthUsed + HORIZONTAL_SPACE);
                parentNeedHeight = parentNeedHeight + lineHeight + VERTICAL_SPACE;
            }
        }
        //确定自己大小

        //根据子View的度量结果，来重新度量自己的ViewGroup
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth : parentNeedWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ? selfHeight : parentNeedHeight;

        setMeasuredDimension(realWidth, realHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
