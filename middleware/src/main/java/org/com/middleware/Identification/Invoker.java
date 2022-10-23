package org.com.middleware.Identification;

import org.com.middleware.abstract_class.AbstractInvoker;
import org.com.middleware.basic.RemoteObject;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class Invoker extends AbstractInvoker {

    protected Registry registry;

    public Invoker(Socket connectionClient) {
        super(connectionClient);
        registry = Registry.getInstance();
    }

    @Override
    public void implementInvoker() {
        System.out.println("Connect Invoker");
        requestMessager = receiveRequestCliente();
        if(!Objects.isNull(requestMessager)){
            //RESOLVER KEY
            RemoteObject rmi = this.registry.lookup("/hello");
            responseMessager = rmi.invokeMethods(requestMessager);
            System.out.println(responseMessager);
            sendResponseCliente();
        }

        try {
            connectionClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
