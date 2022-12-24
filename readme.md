### 1:通过AutoService 实现组件化通信

### 2:通过AIDL实现WebView独立进程与主进程之间的跨进程通信


跨进程通信，我们一般使用aidl来实现，而aidl是采用cs架构，一端是client,一端是server端

我们把主进程当成server端，继承 IWebViewProcessToMainProcessInterface.Stub() 来实现服务端的服务