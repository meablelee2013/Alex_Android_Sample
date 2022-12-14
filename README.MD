### 每个module都会生成如下的文件
```
public final class ThirdActivity$Route {
  private static HashMap routerMaps = new HashMap<String,Class>();

  static {
    routerMaps.put("ThirdActivity",ThirdActivity.class);
  }

  public static Class getTarget(String path) {
    return (Class)routerMaps.get(path);
  }
}
```

或者
```
public final class Router2Activity$Route {
  private static HashMap routerMaps = new HashMap<String,Class>();

  static {
    routerMaps.put("router1",Router1Activity.class);
    routerMaps.put("MainActivity",MainActivity.class);
    routerMaps.put("router2",Router2Activity.class);
  }

  public static Class getTarget(String path) {
    return (Class)routerMaps.get(path);
  }
}


```