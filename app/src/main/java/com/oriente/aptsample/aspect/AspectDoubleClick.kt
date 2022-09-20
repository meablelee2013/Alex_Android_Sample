@file:Suppress("unused")

package com.oriente.aptsample.aspect

import android.text.TextUtils
import android.util.Log
import android.view.View
import com.oriente.aptsample.R
import com.oriente.aptsample.utility.NoDoubleClickUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect


/**
 * Aspect file used to add logging logic at compile time to common actions
 */
@Aspect
class AspectDoubleClick {

    val DOUBLE_CLICK = "DOUBLE_CLICK"
    private var canDoubleClick = false

    @Around("execution(* android.view.View.OnClickListener.onClick(..)) || execution(* android.content.DialogInterface.OnClickListener.onClick(..))")
    fun onClick(joinPoint: ProceedingJoinPoint) {
        Log.d("alex", "execution(* android.view.View.OnClickListener.onClick(..)")
        val objects = joinPoint.args
        if (objects != null && objects.isNotEmpty() && objects[0] is View) {
            val view = objects[0] as View
            if (view != null && view.getTag(R.id.doubleClick) is String
                && TextUtils.equals(view.getTag(R.id.doubleClick) as String, DOUBLE_CLICK)
            ) {
                canDoubleClick = true
            }
            val doubleClick: Boolean = NoDoubleClickUtils.isDoubleClick()
            Log.d("alex", "doubleClick=" + doubleClick)
            if (canDoubleClick || !doubleClick) {
                proceed(joinPoint)
                canDoubleClick = false
            }
        } else {
            proceed(joinPoint)
        }
    }

    private fun proceed(joinPoint: ProceedingJoinPoint) {
        try {
            joinPoint.proceed()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }

}