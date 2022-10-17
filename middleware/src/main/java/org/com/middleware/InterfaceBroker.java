package org.com.middleware;

import org.com.application.HelloWorld;

public class InterfaceBroker {

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        MyMiddleware myMiddlewareServer = new MyMiddleware();
        myMiddlewareServer.addClassMethods(helloWorld);
        myMiddlewareServer.start(8080);
    }
}
