package com.oriente.aptsample.sample1.injectcustomobjectwithinjectinconstructor

import javax.inject.Inject


//依靠在构造函数上添加@Inject可以完成注入
class NetworkService @Inject constructor()