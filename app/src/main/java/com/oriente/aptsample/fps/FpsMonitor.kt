package com.oriente.aptsample.fps

import android.os.Handler
import android.os.Looper
import android.view.Choreographer

object FpsMonitor {

    private const val FPS_INTERVAL_TIME = 1000L

    /**
     * 1秒内执行回调的次数  即fps
     */
    private var mCount = 0
    private val mMonitorListeners = mutableListOf<(Int) -> Unit>()

    @Volatile
    private var mIsStartMonitor = false
    private val mMonitorFrameCallback by lazy { MonitorFrameCallback() }
    private val mMainHandler by lazy { Handler(Looper.getMainLooper()) }

    fun startMonitor(listener: (Int) -> Unit) {
        mMonitorListeners.add(listener)
        if (mIsStartMonitor) {
            return
        }
        mIsStartMonitor = true
        Choreographer.getInstance().postFrameCallback(mMonitorFrameCallback)
        //1秒后结算 count次数
        mMainHandler.postDelayed(mMonitorFrameCallback, FPS_INTERVAL_TIME)
    }

    fun stopMonitor() {
        mIsStartMonitor = false
        mCount = 0
        Choreographer.getInstance().removeFrameCallback(mMonitorFrameCallback)
        mMainHandler.removeCallbacks(mMonitorFrameCallback)
    }

    private class MonitorFrameCallback : Choreographer.FrameCallback, Runnable {

        //VSYNC信号到了，且处理到当前异步消息了，才会回调这里
        override fun doFrame(frameTimeNanos: Long) {
            //次数+1  1秒内
            mCount++
            //继续下一次 监听VSYNC信号
            Choreographer.getInstance().postFrameCallback(this)
        }

        override fun run() {
            //将count次数传递给外面
            mMonitorListeners.forEach {
                it.invoke(mCount)
            }
            mCount = 0
            //继续发延迟消息  等到1秒后统计count次数
            mMainHandler.postDelayed(this, FPS_INTERVAL_TIME)
        }
    }

}