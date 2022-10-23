package org.com.middleware.abstract_class;

import org.com.middleware.messager.Marshaller;
import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public abstract class AbstractInvoker implements Runnable {
    protected final Marshaller marshaller;
    protected Socket connectionClient;
    protected RequestMessage requestMessager;
    protected ResponseMessage responseMessager;

    public AbstractInvoker(Socket connectionClient)
    {
        this.connectionClient = connectionClient;
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

    @Override
    public void run() {
        implementInvoker();
    }

    public abstract void implementInvoker();

}
