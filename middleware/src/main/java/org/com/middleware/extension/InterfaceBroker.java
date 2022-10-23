package org.com.middleware.extension;

import org.com.application.HelloWorld;


public class InterfaceBroker {
    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        MyMiddleware myMiddlewareServer = new MyMiddleware();
        Interceptor interceptor = Interceptor.getInstance();

        myMiddlewareServer.addClassMethods(helloWorld);
        interceptor.registerHook(helloWorld.getClass().getDeclaredMethods());
        myMiddlewareServer.start(8080);
    }
}
