package org.com.middleware;

import org.com.middleware.messager.RequestMessager;
import org.com.middleware.messager.ResponseMessager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Invoker implements Runnable {
    private Marshaller marshaller;
    private Socket connectionClient;
    private RemoteObject remoteObject;
    RequestMessager requestMessager;
    ResponseMessager responseMessager;

    public Invoker(Socket connectionClient) {
        this.connectionClient = connectionClient;
        this.marshaller = new Marshaller();
        //this.requestMessager = new RequestMessager();
        this.responseMessager = new ResponseMessager();
        this.remoteObject = RemoteObject.getInstance();
    }

    @Override
    public void run() {
        System.out.println("Connect Invoker");
        //receive mensseger to client
        receiveRequestCliente();
        //enviar request para o objeto remoto
        //receber response do objeto remoto


        //enviar o response para o cliente
        //sendResponseCliente();

    }

    public void receiveRequestCliente(){
        //desserializar menssagem
        //invoca os metodos da classe remota passando a requisição
        //espera a resposta da invocação
        try {
            BufferedInputStream in = new BufferedInputStream(connectionClient.getInputStream());
            // requestMessager = invoke
             requestMessager = new RequestMessager("POST", "/name", "teste");
             responseMessager = remoteObject.invokeMethods(requestMessager);
        } catch (IOException e) {
            System.err.println("erro ao tentar receber mensagem: " + e.getStackTrace());
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
