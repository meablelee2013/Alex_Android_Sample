### 构造函数上加@Inject 可以注入自定义的Object
### 使用@Binds 注入实现类与接口 参数是实现类，返回值是接口
### 使用#Provides 注入实例（比喻第三方类库的实体类，自己无法更改构造函数）


### hilt usage guide

### 1 注入一个接口 （Bind方法）

假设一个这样的场景，activity需要使用TestInterface的接口实例，实现则来自TestImpl,此时我们无法使用@Inject去修饰接口的构造函数，因为接口根本没有构造函数

```
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    @Inject
//    lateinit var analyticsAdapter: AnalyticsAdapter

    @Inject
    lateinit var testInterface: TestInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        analyticsAdapter.analytics()

        testInterface.doSomeThing()
    }
}
```

```
interface TestInterface {

    fun  doSomeThing()
}
```

```

class TestImpl @Inject constructor() : TestInterface {
    override fun doSomeThing() {
        Log.d("alex", "do something")
    }
}

```

于是，我们新建一个hilt容器，使用@Module注解，注意容器必须是抽象类，然后新建一个方法，方法也必须是抽象方法，方法使用@Bind注解，方法参数为接口的实现，方法的返回就是我们需要绑定的接口

```

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


/**
 * 新建一个抽你类，方法也必须是一个抽象方法，抽象类使用@Module注解，方法使用@Bind注解，方法参数为接口的实现，方法的返回值为需要绑定的接口
 */
@Module
@InstallIn(ActivityComponent::class)
abstract class TestModule {

    @Binds
    abstract fun bindTestInterface(testImpl: TestImpl):TestInterface
}

```

## 注入一个不能修改的类（Provide方法）
除了接口无法通过构造函数注入实例外，还有第三方库如：Retrofit,OKHttpClient 等都无法通过构造函数注入，这时需要使用@Provides注入实例。
