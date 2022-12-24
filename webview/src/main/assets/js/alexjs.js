var alexjs = {};
alexjs.os = {};
alexjs.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
alexjs.os.isAndroid = !alexjs.os.isIOS;
alexjs.callbacks = {}

alexjs.callback = function (callbackname, response) {
   var callbackobject = alexjs.callbacks[callbackname];
   console.log("xxxx"+callbackname);
   if (callbackobject !== undefined){
       if(callbackobject.callback != undefined){
          console.log("xxxxxx"+response);
            var ret = callbackobject.callback(response);
           if(ret === false){
               return
           }
           delete alexjs.callbacks[callbackname];
       }
   }
}

alexjs.callNativeAction = function(commandname, parameters){
    console.log("alexjs callNativeAction")
    var request = {};
    request.name = commandname;
    request.param = parameters;
    if(window.alexjs.os.isAndroid){
        console.log("android take native action" + JSON.stringify(request));
        window.alexWebView.callNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.alexWebView.postMessage(JSON.stringify(request))
    }
}

alexjs.callNativeActionWithCallback = function(commandname, parameters, callback) {
    var callbackname = "nativetojs_callback_" +  (new Date()).getTime() + "_" + Math.floor(Math.random() * 10000);
    alexjs.callbacks[callbackname] = {callback:callback};

    var request = {};
    request.name = commandname;
    request.param = parameters;
    request.param.callbackname = callbackname;
    if(window.alexjs.os.isAndroid){
        window.alexWebView.callNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.alexWebView.postMessage(JSON.stringify(request))
    }
}

window.alexjs = alexjs;
