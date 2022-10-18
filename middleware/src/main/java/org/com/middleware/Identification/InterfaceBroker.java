package org.com.middleware.Identification;

import org.com.application.HelloWorld;


public class InterfaceBroker {

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        MyMiddleware myMiddlewareServer = new MyMiddleware();
        myMiddlewareServer.createRMI(helloWorld);
        myMiddlewareServer.start(8080);
    }
}
