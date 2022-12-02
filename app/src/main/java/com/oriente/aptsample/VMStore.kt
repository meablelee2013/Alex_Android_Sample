package com.oriente.aptsample

import androidx.activity.ComponentActivity
import androidx.lifecycle.*


/**
 * 根据作用域创建viewModel，自定义ViewModelStoreOwner
 */
private val vMStores = HashMap<String, VMStore>()

fun LifecycleOwner.injectViewModel() {
    //根据作用域创建商店
    this::class.java.declaredFields.forEach { field ->
        field.getAnnotation(VMScope::class.java)?.also { scope ->
            val element = scope.scopeName
            var store: VMStore
            if (vMStores.keys.contains(element)) {
                store = vMStores[element]!!
            } else {
                store = VMStore()
                vMStores[element] = store
            }
            store.register(this)
            val clazz = field.type as Class<ViewModel>
            val vm = ViewModelProvider(store, ViewModelProvider.NewInstanceFactory()).get(clazz)
            field.set(this, vm)
        }
    }
}

class VMStore : ViewModelStoreOwner {

    private val bindTargets = ArrayList<LifecycleOwner>()
    private var vmStore: ViewModelStore? = null

    fun register(host: LifecycleOwner) {
        if (!bindTargets.contains(host)) {
            bindTargets.add(host)
            host.lifecycle.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        if (host is ComponentActivity) {
                            if (!host.isChangingConfigurations) {
                                host.lifecycle.removeObserver(this)
                                bindTargets.remove(host)
                                if (bindTargets.isEmpty()) {//如果当前商店没有关联对象，则释放资源
                                    vMStores.entries.find { it.value == this@VMStore }?.also {
                                        vmStore?.clear()
                                        vMStores.remove(it.key)
                                    }
                                }
                            }
                        }
                    }
                }
            })
        }
    }

    override fun getViewModelStore(): ViewModelStore {
        if (vmStore == null) vmStore = ViewModelStore()
        return vmStore!!
    }
}