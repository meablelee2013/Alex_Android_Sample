package com.oriente.aptsample

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

operator fun TextView.provideDelegate(value: Any?, property: KProperty<*>) = object : ReadWriteProperty<Any?, String?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String? = text as String?

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        text = value
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var tvState1: TextView = findViewById(R.id.tvState1)
        var tvState11: TextView = findViewById(R.id.tvState11)
        var tvState21: TextView = findViewById(R.id.tvState21)

        var stateFlag1: String? by tvState1
        var stateFlag11: String? by tvState11
        var stateFlag21: String? by tvState21

        R.id.btn1.onClick(this) {
            thread {
                runOnUiThread {
                    stateFlag1 = "正在赶往1楼的过程中..."
                    stateFlag11 = stateFlag1
                    stateFlag21 = stateFlag11
                }
                Thread.sleep(10000)
                runOnUiThread {
                    stateFlag1 = "恭喜你，1楼到了"
                    stateFlag11 = stateFlag1
                    stateFlag21 = stateFlag11
                }

            }

        }
        R.id.btn11.onClick(this) {
            thread {
                runOnUiThread {
                    stateFlag11 = "正在赶往11楼的过程中..."
                    stateFlag1 = stateFlag11
                    stateFlag21 = stateFlag1
                }
                Thread.sleep(10000)
                runOnUiThread {
                    stateFlag11 = "恭喜你，11楼到了"
                    stateFlag1 = stateFlag11
                    stateFlag21 = stateFlag1
                }

            }
        }
        R.id.btn21.onClick(this) {
            thread {
                runOnUiThread {
                    stateFlag21 = "正在赶往21楼的过程中..."
                    stateFlag1 = stateFlag21
                    stateFlag11 = stateFlag1
                }
                Thread.sleep(10000)
                runOnUiThread {
                    stateFlag21 = "恭喜你，21楼到了"
                    stateFlag1 = stateFlag21
                    stateFlag11 = stateFlag1
                }

            }
        }
    }
}

//给Int添加扩展方法
fun Int.onClick(activity: Activity, click: (view: View) -> Unit) {
    activity.findViewById<View>(this).setOnClickListener {
        println("onClick")
        click(it)
    }
}

