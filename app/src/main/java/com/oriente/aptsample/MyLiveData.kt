package com.oriente.aptsample

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.reflect.Field
import java.lang.reflect.Method

class MyLiveData<T> : MutableLiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        hook(observer)
    }

    private fun hook(observer: Observer<in T>) {
        try {
            // Resolve the issue of sticky by setting mLastVersion = mVersion
            val liveDataClass = LiveData::class.java

            // Find the map that saves LifecycleBoundObserver
            val mObserversField: Field = liveDataClass.getDeclaredField("mObservers")
            mObserversField.isAccessible = true
            val mObserversObj: Any = mObserversField.get(this)

            val mObserversClass: Class<*> = mObserversObj.javaClass

            // Get the 'get' method information
            val mapMethodGet: Method = mObserversClass.getDeclaredMethod("get", Object::class.java)
            mapMethodGet.isAccessible = true

            // Get the Map.Entry<Observer<? super T>, ObserverWrapper> instance by calling mObserver.get(observer)
            val obj: Any = mapMethodGet.invoke(mObserversObj, observer)

            // Get the class of Map.Entry
            val observerWrapperClass: Class<*> = obj.javaClass

            // Get the information of the 'getValue' method of Map.Entry
            val getValue: Method = observerWrapperClass.getDeclaredMethod("getValue")
            getValue.isAccessible = true

            // Execute the 'getValue' method of the Map.Entry instance to get the LifecycleBoundObserver instance
            val wrapper: Any = getValue.invoke(obj)
            val lifecycleBoundObserverClass: Class<*> = wrapper.javaClass

            // Get the class information of the parent class because mLastVersion is in the parent class
            val wrapperClass: Class<*> = lifecycleBoundObserverClass.superclass
            val mLastVersionField: Field = wrapperClass.getDeclaredField("mLastVersion")
            mLastVersionField.isAccessible = true

            // Get the value of mVersion maintained in LiveData
            val mVersionField: Field = liveDataClass.getDeclaredField("mVersion")
            mVersionField.isAccessible = true
            val mVersion: Int = mVersionField.getInt(this)

            // Modify the value in mLastVersion
            mLastVersionField.set(wrapper, mVersion)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
