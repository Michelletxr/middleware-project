package org.com.application;
import org.com.application.HelloWorld2;
import org.com.application.HelloWorld;
import org.com.middleware.basic.MyMiddleware;
import org.com.middleware.extension.infra.model.Protocol;

public class InterfaceBroker {

  public static void main(String[] args) {
    HelloWorld helloWorld = new HelloWorld();
    HelloWorld2 helloWorld2= new HelloWorld2();
    Calculator cal = new Calculator();

    MyMiddleware myMiddlewareServer = new MyMiddleware();

    //basic
   // myMiddlewareServer.registerRMI(cal);
    //myMiddlewareServer.registerRMI(helloWorld);
    //myMiddlewareServer.registerRMI(helloWorld2);


  //extension TCP
   // myMiddlewareServer.registerRMI(cal);
    //myMiddlewareServer.registerHooks();
    //myMiddlewareServer.registerRMI(helloWorld);
   // myMiddlewareServer.registerHooks();
   // myMiddlewareServer.registerRMI(helloWorld2);
    //myMiddlewareServer.registerHooks();

    //extension TCP
    myMiddlewareServer.defineProtocol(Protocol.UDP);
    myMiddlewareServer.registerRMI(helloWorld);
    myMiddlewareServer.registerHooks();
    //myMiddlewareServer.registerRMI(hello2);
    //myMiddlewareServer.registerHooks();


    myMiddlewareServer.start(8080);
  }
}
