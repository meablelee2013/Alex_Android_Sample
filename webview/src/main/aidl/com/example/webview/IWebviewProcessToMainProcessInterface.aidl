// IWebviewProcessToMainProcessInterface.aidl
package com.example.webview;

import com.example.webview.ICallbackFromMainprocessToWebViewProcessInterface;

interface IWebviewProcessToMainProcessInterface {

   void handleWebCommand(String commandName,String jsonParmas,in ICallbackFromMainprocessToWebViewProcessInterface callback);
}