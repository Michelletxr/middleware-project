package org.com.middleware.basic;

import org.com.middleware.interfaces.Iinvoker;
import org.com.middleware.identification.Registry;
import org.com.middleware.messager.Marshaller;
import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;

public class InvokerBasic implements Runnable, Iinvoker {
    private   Marshaller marshaller;
    private Socket connectionClient;
    private RequestMessage requestMessager;
    private ResponseMessage responseMessager;
    private Registry registry;
    public InvokerBasic() {
        this.registry = Registry.getInstance();
        this.marshaller = new Marshaller();
        this.requestMessager = new RequestMessage();
        this.responseMessager = new ResponseMessage();
    }

    public RequestMessage receiveRequestCliente(){
        RequestMessage request = new RequestMessage();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connectionClient.getInputStream()));
            request = this.marshaller.unMarchall(in);
        } catch (IOException e) {
            System.err.println("erro ao tentar receber mensagem: " + e.getStackTrace());
        }
        return  request;
    }

    public void sendResponseCliente() {
        try {
            DataOutputStream out = new DataOutputStream(connectionClient.getOutputStream());
            String responseSerializer = marshaller.marshall(responseMessager);
            out.writeBytes(responseSerializer+ "\r\n");
        } catch (IOException e) {
            System.err.println("erro ao tenatar enviar mensagem: " + e.getStackTrace());
        }
    }

    public void setConnectionClient(Socket connectionClient){
        this.connectionClient = connectionClient;
    }
    @Override
    public void run() {
        implementInvoker();
    }
    @Override
    public void implementInvoker() {
        this.requestMessager = receiveRequestCliente();
        System.out.println("req"+requestMessager);
        if(!Objects.isNull(requestMessager)){
            RemoteObject rmi = null;
            try {
                rmi = this.registry.lookup(requestMessager.getKey());
                this.responseMessager = rmi.invokeMethods(this.requestMessager);
            } catch (Exception e) {
                responseMessager.setStatusCod(500);
                responseMessager.setResponseBody(e.getMessage());
            }
           sendResponseCliente();
        }

    }
}
