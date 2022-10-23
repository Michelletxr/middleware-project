package org.com.middleware.identification;

import org.com.application.Hello2;
import org.com.application.HelloWorld;


public class InterfaceBroker {
    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        Hello2 heloo = new Hello2();
        MyMiddleware myMiddlewareServer = new MyMiddleware();
        myMiddlewareServer.createRMI(helloWorld);
        myMiddlewareServer.createRMI(heloo);
        myMiddlewareServer.start(8080);
    }
}
