package org.com.middleware;
import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Invoker implements Runnable {
    private final Marshaller marshaller;
    private  Socket connectionClient;
    private final RemoteObject remoteObject;
    RequestMessage requestMessager;
    ResponseMessage responseMessager;

    public Invoker(Socket connectionClient) {
        this.connectionClient = connectionClient;
        this.marshaller = new Marshaller();
        this.requestMessager = new RequestMessage();
        this.responseMessager = new ResponseMessage();
        this.remoteObject = RemoteObject.getInstance();
    }

    @Override
    public void run() {
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

    //desserializar menssagem
    //invoca os metodos da classe remota passando a requisição
    //espera a resposta da invocação
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
