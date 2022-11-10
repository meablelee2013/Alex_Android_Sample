package com.oriente.aptsample

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val screen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mySplashMainIcon: ImageView = findViewById(R.id.splash_main_icon)
        screen.setOnExitAnimationListener { splashScreenView ->
            val lineStartY: Int = getRelativeTop2(mySplashMainIcon)
            val animY = ObjectAnimator.ofFloat(
                splashScreenView.iconView,
                View.Y,
                lineStartY.toFloat()
            )
            //Just in case you wish to run multiple animations
            /*val animX = ObjectAnimator.ofFloat(
                splashScreenView.iconView,
                View.X,
                mySplashIcon2.x
            )*/

            val alpha = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.ALPHA,
                1f,
                0f
            )
            alpha.interpolator = AccelerateInterpolator()
            alpha.duration = 200L

            val traverseSet = AnimatorSet().apply {
                interpolator = OvershootInterpolator()
                duration = 500L
                playTogether(/*animX,*/ animY)
            }

            AnimatorSet().apply {
                play(traverseSet).before(alpha)
                doOnEnd {
                    splashScreenView.remove()
                }
                start()
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            doSomeWork() // can do initializations on IO/Worker Thread
            launchNextScreen() // back on UI/Main thread
        }
    }
    private suspend fun doSomeWork() = withContext(Dispatchers.IO) {
        delay(5000L) // pretend we are doing some IO operation
    }

    private fun launchNextScreen() {
        startActivity(Intent(this, ClientActivity::class.java))
        finish()
    }

    private fun getRelativeTop2(view: View): Int {
        val parent = view.parent as View
        val parentLocation = IntArray(2)
        val viewLocation = IntArray(2)
        view.getLocationOnScreen(viewLocation)
        parent.getLocationOnScreen(parentLocation)
        return viewLocation[1] - (parentLocation[1]*2)
    }
}