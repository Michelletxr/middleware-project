package org.com.middleware.basic;

import org.com.application.HelloWorld;
import org.com.middleware.identification.infraestrutura.model.Protocol;

public class InterfaceBroker {

  public static void main(String[] args) {
    HelloWorld helloWorld = new HelloWorld();
    MyMiddleware myMiddlewareServer = new MyMiddleware(Protocol.TCP);
    myMiddlewareServer.addClassMethods(helloWorld);
    myMiddlewareServer.start(8080);
  }
}
