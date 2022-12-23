// IWebviewProcessToMainProcessInterface.aidl
package com.example.webview;

// Declare any non-default types here with import statements

interface IWebviewProcessToMainProcessInterface {


   void handleWebCommand(String commandName,String jsonParmas);
}