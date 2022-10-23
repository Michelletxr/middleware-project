package org.com.middleware.identification;

import org.com.middleware.abstracts.AbstractMyMiddleware;
import org.com.middleware.basic.RemoteObject;


import java.io.IOException;

public class MyMiddleware extends AbstractMyMiddleware {
    private Object stub;
    private Registry registry;
    public MyMiddleware() {
        this.rmi = new RemoteObject();
        this.registry = Registry.getInstance();
    }

    public void createRMI(Object object){
        stub = object;
        this.addClassMethods(stub);
        System.out.println("bind: " + rmi);
        registry.bind(objectId, rmi);
    }

    public void start(int port) {
        try {
            new ServerRequestHandler(port).startServer();
        } catch (IOException e) {
            System.err.println("erro ao tentar estabelecer conexão com o servidor");
        }
    }
}
