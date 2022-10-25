package org.com.middleware.basic;

import org.com.middleware.abstracts.AbstractInvoker;
import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;


import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

//implement identification
public class Invoker extends AbstractInvoker {
    private  Socket connectionClient;
    private  RemoteObject remoteObject;
    RequestMessage requestMessager;
    ResponseMessage responseMessager;


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
