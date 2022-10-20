package org.com.middleware.Identification;
import org.com.middleware.basic.Marshaller;
import org.com.middleware.basic.RemoteObject;
import org.com.middleware.basic.messager.RequestMessage;
import org.com.middleware.basic.messager.ResponseMessage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;

public class Invoker {

    private final Marshaller marshaller;
    private Socket connectionClient;
    private Registry registry;
    RequestMessage requestMessager;
    ResponseMessage responseMessager;

    public Invoker(Socket connectionClient)
    {
        this.connectionClient = connectionClient;
        this.marshaller = new Marshaller();
        this.requestMessager = new RequestMessage();
        this.responseMessager = new ResponseMessage();
        this.registry = Registry.getInstance();
    }

    public void run() {
        System.out.println("Connect Invoker");
         requestMessager = receiveRequestCliente();
        if(!Objects.isNull(requestMessager)){
           RemoteObject rmi = this.registry.lookup(requestMessager.getRouter());
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

    public RequestMessage receiveRequestCliente(){
        RequestMessage request = new RequestMessage();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connectionClient.getInputStream()));
            request = this.marshaller.unMarchall(in);
        } catch (IOException e) {
            System.err.println("erro ao tentar receber mensagem: " + e.getStackTrace());
        }
        System.out.println("resultado final request " + request);
        return  request;
    }

    public void sendResponseCliente() {
        try {

            DataOutputStream out = new DataOutputStream(connectionClient.getOutputStream());
            String responseSerializer = marshaller.marshall(responseMessager);
            out.writeBytes(responseSerializer+ "\r\n");
            System.out.println("enviado para servidor....." + responseSerializer);
        } catch (IOException e) {
            System.err.println("erro ao tenatar enviar mensagem: " + e.getStackTrace());
        }
    }
}
