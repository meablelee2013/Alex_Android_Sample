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

于是，我们新建一个hilt容器，使用@Module注解，注意容器必须是抽象类，然后新建一个方法，方法也必须是抽象方法，方法使用@Bind注解，方法参数为接口的实现，方法的返回就是我们需要绑定的接口

```
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
  
  @Module
  @InstallIn(ActivityComponent::class)
  abstract class TestModule {

  @Binds
  abstract fun bindTestInterface(testImpl: TestImpl):TestInterface
  }

```