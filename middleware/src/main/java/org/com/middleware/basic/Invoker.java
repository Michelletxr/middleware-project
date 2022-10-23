package org.com.middleware.basic;

import org.com.middleware.abstract_class.AbstractInvoker;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;


public class Invoker extends AbstractInvoker {
    private  RemoteObject remoteObject;

    public Invoker(Socket connectionClient) {
        super(connectionClient);
        remoteObject = RemoteObject.getInstance();
    }

    @Override
    public void implementInvoker() {
        System.out.println("Connect Invoker");
        requestMessager = receiveRequestCliente();
        if(!Objects.isNull(requestMessager)){
            responseMessager = this.remoteObject.invokeMethods(requestMessager);
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
