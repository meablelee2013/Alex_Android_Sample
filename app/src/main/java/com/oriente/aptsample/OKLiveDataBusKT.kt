package com.oriente.aptsample


import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * 单例模式 设置了一个开关，通过函数参数决定是否启用黏性事件（有关闭黏性的开关） KT的版本
 */
object OKLiveDataBusKT {

    private val bus: MutableMap<String, BusMutableLiveData<Any>> by lazy { HashMap() }

    @Synchronized
    fun <T> with(key: String, type: Class<T>, isStick: Boolean = true): BusMutableLiveData<T> {
        //判断是否有重复的KEY
        if (!bus.containsKey(key)) {
            bus[key] = BusMutableLiveData(isStick)//运算符重载
        }
        return bus[key] as BusMutableLiveData<T>//强制类型转换
    }

    // 私有主构造，不让外界new，那么就要写次构造函数调用主构造进行激活
    //前面那个T会影响后面那个T
    class BusMutableLiveData<T> private constructor() : MutableLiveData<T>() {

        //粘性开关
        var isStick: Boolean = false

        // 次构造调用主构造进行激活
        constructor(isStick: Boolean) : this() {
            this.isStick = isStick
        }

        // 对系统代码进行重载，目的就是实现带开关的粘性清除(可以开，可以关)
        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {/*这句话是不能删掉的，hook操作不能破坏原有系统，只是多开了一条路而已；
并且执行下面这句代码的时候，只是起到了一个注册而已；
真正执行代码是由宿主的生命周期变化从而触发的*/
            super.observe(owner, observer) // 这句会执行父类的  // 启用系统的功能  不写就破坏了

            //使用参数，决定是否启用粘性
            if (!isStick) {
                hook(observer = observer)
                Log.d("derry", "Kotlin版本的 不启用黏性")
            } else {
                Log.d("derry", "Kotlin版本的 启用黏性")
            }
        }

        //使用反射，去掉数据粘性
        private fun hook(observer: Observer<in T>) {
            // TODO 1.得到mLastVersion
            // 获取到LivData的类中的mObservers对象，后面的.java将其转为java的
            val liveDataClass = LiveData::class.java

            //拿到字段
            val mObserversField: Field = liveDataClass.getDeclaredField("mObservers")
            mObserversField.isAccessible = true // 私有修饰也可以访问

            // 获取到这个成员变量的对象  Any == Object
            val mObserversObject: Any = mObserversField.get(this)

            // 得到map对象的class对象    private SafeIterableMap<Observer<? super T>, ObserverWrapper> mObservers =
            val mObserversClass: Class<*> = mObserversObject.javaClass

            // 获取到mObservers对象的get方法    protected Entry<K, V> get(K k) {
            val get: Method = mObserversClass.getDeclaredMethod("get", Any::class.java)
            get.isAccessible = true // 私有修饰也可以访问

            // 执行get方法
            val invokeEntry: Any = get.invoke(mObserversObject, observer)

            // 获取entry中的value    is "AAA" is String     is是判断类型 as是转换类型
            var observerWraper: Any? = null
            if (invokeEntry != null && invokeEntry is Map.Entry<*, *>) {
                observerWraper = invokeEntry.value
            }
            if (observerWraper == null) {
                throw NullPointerException("observerWraper is null")
            }

            // 得到observerWraperr的类对象
            val supperClass: Class<*> = observerWraper.javaClass.superclass
            val mLastVersion: Field = supperClass.getDeclaredField("mLastVersion")
            mLastVersion.isAccessible = true

            // TODO 2.得到mVersion
            val mVersion: Field = liveDataClass.getDeclaredField("mVersion")
            mVersion.isAccessible = true//私有的也可以访问

            // TODO 3.mLastVersion=mVersion
            val mVersionValue: Any = mVersion.get(this)
            mLastVersion.set(observerWraper, mVersionValue)//状态对齐
        }
    }
}
