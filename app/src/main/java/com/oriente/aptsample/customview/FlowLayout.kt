package com.oriente.aptsample.customview

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

class FlowLayout : ViewGroup {
    private val TAG = "FlowLayout"
    private val mHorizontalSpacing: Int = dp2px(16.0f) //每个item横向间距

    private val mVerticalSpacing: Int = dp2px(8.0f) //每个item横向间距
    var allLines = ArrayList<List<View>>()// 记录所有的行，一行一行的存储，用于layout

    var lineHeights = ArrayList<Int>() // 记录每一行的行高，用于layout


    //new 创建
    constructor(context: Context?) : super(context) {}

    //xml 反射创建
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    //三个参数  主题
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}
    //四个参数 用于自定义属性

    /**
     * 1.获取子View的Count，并遍历这些childView
     * 2.当childView可见的情况下，测量childView的宽高
     *      2.1 将childView的LayoutParams转变成MeasureSpec(getChildMeasureSpec)
     *      2.2 调用childView.measure(widthSpec,heightSpec)
     *      2.3 可以通过childView.getMeasureWidth 以及childView.getMeasureHeight获取childView测试之后的真实paym
     * 3.判断一行是否放得下，也就是需要判断已经使用的宽度+childView.getMeasureWidth+horiSpace 是否大于父控件的宽度
     *      并记录每一行的view,每一行的最大高度
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        clearMeasureParams()
        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)//ViewGroup解析的父亲给我的宽度 如果窗口的layout_width给的是具体值，当这个selfWidth就等于给的具体值
        val selfHeight = MeasureSpec.getSize(heightMeasureSpec)//ViewGroup解析的父亲给我的高度，逻辑同上

        val widthMode = MeasureSpec.getMode(widthMeasureSpec) //当前容器是什么模式
        val heightMode = MeasureSpec.getMode(heightMeasureSpec) //当前容器是什么模式

        var lineViews = ArrayList<View>() //保存一行中所有的View
        //记录这一行已经使用了多宽  每添加一个子View，需要记录当前的位置，当再放下一个子view时需要基于当前位置考虑是否放得下
        var lineWidthUsed = 0
        var lineHeightMax = 0//一行的行高

        var parentNeededWidth = 0//measure过程中，子View要求的父ViewGroup的宽
        var parentNeededHeight = 0//measure过程中，子View要求的父ViewGroup的高


        //先度量孩子
        for (childIndex in 0 until childCount) {
            val childView = getChildAt(childIndex)
            val layoutParams = childView.layoutParams
            if (childView.visibility != GONE) {
                //如何测量子View
                /**
                 * 第一步：通过getChildMeasureSpec将LayoutParams转变成MeasureSpec
                 * 第二步：调用childView.measure
                 * 第三步：获取childView测量之后的宽高
                 */
                //将layoutParams转变成measureSpec
                val childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, layoutParams.width)
                val childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, layoutParams.height)
                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec)

                //childView measure完就可以知道其宽高了
                val childMeasuredWidth = childView.measuredWidth
                val childMeasuredHeight = childView.measuredHeight
                //如果需要换行 首先记录换行前的数据，并将每行的窗口清零
                if (lineWidthUsed + childMeasuredWidth + mHorizontalSpacing > selfWidth) {
                    //换行了，我们需要记录换行前的数据
                    allLines.add(lineViews)
                    lineHeights.add(lineHeightMax)
                    parentNeededWidth += lineWidthUsed + mHorizontalSpacing
                    parentNeededHeight += lineHeightMax + mVerticalSpacing

                    lineViews = ArrayList()
                    lineHeightMax = 0
                    lineWidthUsed = 0

                }
                lineViews.add(childView)
                //每行都会有自己的宽高
                lineWidthUsed += childMeasuredWidth + mHorizontalSpacing
                lineHeightMax = lineHeightMax.coerceAtLeast(childMeasuredHeight)//max
            }
            if (childIndex == childCount - 1) {
                allLines.add(lineViews)
                lineHeights.add(lineHeightMax)
                parentNeededWidth += lineWidthUsed + mHorizontalSpacing
                parentNeededHeight += lineHeightMax + mVerticalSpacing
            }
        }


        //再度量自己  先要知道自己是什么mode

        var width = if (widthMode == MeasureSpec.EXACTLY) {
            selfWidth
        } else {
            parentNeededWidth
        }
        var height = if (heightMode == MeasureSpec.EXACTLY) {
            selfHeight
        } else {
            parentNeededHeight
        }
        setMeasuredDimension(width, height)
    }

    //四个参数 自定义属性
    private fun clearMeasureParams() {
        allLines.clear()
        lineHeights.clear()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val allLinesSize = allLines.size
        var curL = paddingLeft
        var curT = paddingTop
        for (lineIndex in 0 until allLinesSize) {
            val lineViews = allLines[lineIndex]
            val lineHeight = lineHeights[lineIndex]
            val lineCount = lineViews.size
            for (childIndex in 0 until lineCount) {
                val childView = lineViews[childIndex]
                val left = curL
                val top = curT
                val right = left + childView.measuredWidth
                val bottom = top + childView.measuredHeight
                childView.layout(left, top, right, bottom)
                curL = right + mHorizontalSpacing
            }
            curT += lineHeight + mVerticalSpacing
            curL = paddingLeft
        }
    }

    private fun dp2px(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics).toInt()
    }
}