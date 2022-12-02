package com.oriente.aptsample.anrwatchdog

import android.os.Debug
import android.os.Handler
import android.os.Looper
import android.util.Log

/**
 * 一个监控是否有anr的工具类
 * 原理是在子线程中每隔5s向主线程发送消息，然后线程里面sleep 5秒，然后再来查看消息是否被执行
 * 如果没有被执行则表示主线程阻塞了
 */
class ANRWatchDog : Thread() {
    var shutdown: Boolean = false


    interface ANRListener {
        fun appNotResponding(anrError: ANRError)
    }

    interface ANRInterceptor {
        fun intercept(duration: Long): Long
    }

    interface InterruptionListener {
        fun onInterrupted(exception: InterruptedException)
    }

    private val DEFAULT_ANR_LISTENER: ANRListener = object : ANRListener {

        override fun appNotResponding(anrError: ANRError) {

        }
    }

    private val DEFAULT_ANR_INTERCEPTOR: ANRInterceptor = object : ANRInterceptor {
        override fun intercept(duration: Long): Long {
            return 0
        }
    }

    private val DEFAULT_INTERRUPTION_LISTENER: InterruptionListener = object : InterruptionListener {
        override fun onInterrupted(exception: InterruptedException) {
            Log.w("ANRWatchdog", "Interrupted: " + exception.message)
        }
    }
    private val _anrListener = DEFAULT_ANR_LISTENER
    private val _anrInterceptor = DEFAULT_ANR_INTERCEPTOR
    private val _interruptionListener = DEFAULT_INTERRUPTION_LISTENER
    private val _uiHandler: Handler = Handler(Looper.getMainLooper())
    private val _namePrefix = ""
    private val _logThreadsWithoutStackTrace = false
    private val _ignoreDebugger = false

    @Volatile
    private var _tick: Long = 0

    @Volatile
    private var _reported = false

    private val _ticker = Runnable {
        _tick = 0
        _reported = false
    }
    private val _timeoutInterval = 0L
    override fun run() {
        super.run()
        name = "|ANR-WatchDog|"
        var interval: Long = _timeoutInterval
        while (!isInterrupted) {
            var needPost = _tick == 0L
            _tick += interval
            if (needPost) {
                _uiHandler.post(_ticker)
            }
            //休息interval 时间再来查看_tick的值是否==0
            try {
                Thread.sleep(interval)
            } catch (e: InterruptedException) {
                _interruptionListener.onInterrupted(e)
                return
            }
            // If the main thread has not handled _ticker, it is blocked. ANR.
            if (_tick != 0L && !_reported) {
                //post到ui线程中的任务没有被执行，
                if (!_ignoreDebugger && (Debug.isDebuggerConnected() || Debug.waitingForDebugger())) {
                    Log.w(
                        "ANRWatchdog",
                        "An ANR was detected but ignored because the debugger is connected (you can prevent this with setIgnoreDebugger(true))"
                    );
                    _reported = true
                    continue
                }


//                if (_namePrefix != null) {
//                    error = ANRError.New(_tick, _namePrefix, _logThreadsWithoutStackTrace);
//                } else {
//                    error = ANRError.NewMainOnly(_tick);
//                }
                _anrListener.appNotResponding(ANRError())
                interval = _timeoutInterval
                _reported = true;

            }
        }
    }
}