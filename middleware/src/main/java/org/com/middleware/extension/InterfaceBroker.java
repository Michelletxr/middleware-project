package org.com.middleware.extension;
import org.com.middleware.basic.Invoker;
import org.com.middleware.basic.MyMiddleware;
import org.com.application.HelloWorld;


public class InterfaceBroker {
    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();

        MyMiddleware myMiddlewareServer = new MyMiddleware();
        myMiddlewareServer.setInvoker(new Invoker());
        myMiddlewareServer.registerRMI(helloWorld);

        Interceptor interceptor = Interceptor.getInstance();
        interceptor.setObjectId(myMiddlewareServer.objectId);
        interceptor.registerHook(helloWorld.getClass().getDeclaredMethods());

        myMiddlewareServer.start(8080);
    }
}
