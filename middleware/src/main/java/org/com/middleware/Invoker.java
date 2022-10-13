package org.com.middleware;
import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Invoker implements Runnable {
    private final Marshaller marshaller;
    private final Socket connectionClient;
    private final RemoteObject remoteObject;
    RequestMessage requestMessager;
    ResponseMessage responseMessager;

    public Invoker(Socket connectionClient) {
        this.connectionClient = connectionClient;
        this.marshaller = new Marshaller();
        //this.requestMessager = new RequestMessage();
        this.responseMessager = new ResponseMessage();
        this.remoteObject = RemoteObject.getInstance();
    }

    @Override
    public void run() {
        System.out.println("Connect Invoker");
        receiveRequestCliente();
    }

    //desserializar menssagem
    //invoca os metodos da classe remota passando a requisição
    //espera a resposta da invocação
    public void receiveRequestCliente(){

        try {
            BufferedInputStream in = new BufferedInputStream(connectionClient.getInputStream());
            this.requestMessager = this.marshaller.unMarchall(in);
            responseMessager = remoteObject.invokeMethods(requestMessager);
        } catch (IOException e) {
            System.err.println("erro ao tentar receber mensagem: " + e.getStackTrace());
        }
    }

    public void sendResponseCliente() {
        try {
            BufferedOutputStream out = new BufferedOutputStream(connectionClient.getOutputStream());
            String responseSerializer = marshaller.marshall(responseMessager);
            out.write(responseSerializer.getBytes());
        } catch (IOException e) {
            System.err.println("erro ao tenatar enviar mensagem: " + e.getStackTrace());
        }
    }
}
