package org.com;

import org.com.messager.RequestMessager;
import org.com.messager.ResponseMessager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Invoker implements Runnable {
    private Marshaller marshaller;
    private Socket connectionClient;

    RequestMessager requestMessager;
    ResponseMessager responseMessager;

    public Invoker(Socket connectionClient) {
        this.connectionClient = connectionClient;
        this.marshaller = new Marshaller();
        requestMessager = new RequestMessager();
        responseMessager = new ResponseMessager();
    }

    @Override
    public void run() {
        //receive mensseger to client
        receiveRequestCliente();
        //enviar request para o objeto remoto
        //receber response do objeto remoto
        sendResponseCliente();
        //enviar o response para o cliente

    }

    public void receiveRequestCliente(){
        //desserializar menssagem
        try {
            BufferedInputStream in = new BufferedInputStream(connectionClient.getInputStream());
             requestMessager = marshaller.unMarchall(in);
        } catch (IOException e) {
            System.err.println("erro ao tenatar receber mensagem: " + e.getStackTrace());
        }
    }

    public void sendResponseCliente(){
        try {
            BufferedOutputStream out = new BufferedOutputStream(connectionClient.getOutputStream());
            String responseSerializer = marshaller.marshall(responseMessager);
            out.write(responseSerializer.getBytes());
        } catch (IOException e) {
            System.err.println("erro ao tenatar enviar mensagem: " + e.getStackTrace());
        }

    }
}
