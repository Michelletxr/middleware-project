package org.com.application;
import org.com.application.HelloWorld2;
import org.com.application.HelloWorld;
import org.com.middleware.basic.MyMiddleware;
import org.com.middleware.extension.infra.model.Protocol;

public class InterfaceBroker {

  public static void main(String[] args) {
    HelloWorld helloWorld = new HelloWorld();
    HelloWorld2 hello2 = new HelloWorld2();
    Calculator cal = new Calculator();

    MyMiddleware myMiddlewareServer = new MyMiddleware();

    //myMiddlewareServer.registerRMI(cal);
    //myMiddlewareServer.defineProtocol(Protocol.UDP);
    myMiddlewareServer.registerRMI(helloWorld);
    //myMiddlewareServer.registerHooks();
    //myMiddlewareServer.registerRMI(hello2);
    //myMiddlewareServer.registerHooks();


    myMiddlewareServer.start(8080);
  }
}
