package com.oriente.aptsample.learnkotlin.delegate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oriente.aptsample.R
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

/**
 * 委托不能写在方法里，而应该写成类的成员变量
 */

class DelegateActivity : AppCompatActivity() {

    operator fun String.provideDelegate(activity: DelegateActivity, property: KProperty<*>) = object : ReadWriteProperty<DelegateActivity?, String> {
        override fun getValue(thisRef: DelegateActivity?, property: KProperty<*>): String {
            return this@provideDelegate
        }

        override fun setValue(thisRef: DelegateActivity?, property: KProperty<*>, value: String) {
            println("setValue:thisRef  $thisRef  property   $property   ")
            //通过kotlin反射，修改name2的value
            //会报错 java.lang.IllegalArgumentException: field com.oriente.aptsample.learnkotlin.delegate.DelegateActivity.delegateName2$delegate has type kotlin.properties.ReadWriteProperty, got java.lang.String
            property.javaField?.isAccessible =true
            property.javaField?.set(thisRef,value)
        }

    }

    //委托不能写在方法内
    var delegateName1: String = "is success"
    var delegateName2: String by ::delegateName1 //使用官方委托可以
    //var delegateName2: String by delegateName1 使用自定义委托不可以

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "DelegateActivity"


        //委托不能写在方法内
//        var delegateName1: String = "is success"
        //var delegateName2: String by ::delegateName1//在方法里面，官方也不支持，因为官方也拿不到==thisRef :null
//        var delegateName2: String by delegateName1 //使用自定义委托也不行，编译不通过

        delegateName2 = "李耳"
    }
}