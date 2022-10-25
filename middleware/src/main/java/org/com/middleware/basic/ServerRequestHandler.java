package org.com.middleware.basic;

import org.com.middleware.abstracts.AbstractInvoker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRequestHandler {

    private ServerSocket serverSocket;
    private AbstractInvoker invoker;
    private  int port;

    public ServerRequestHandler(int port, AbstractInvoker invoker){
        this.port = port;
        this.invoker = invoker;
    }

    public void startServer() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("start service in port:" + serverSocket.getLocalPort());
        while (true){

            Socket connection = serverSocket.accept();
            dispatchToInvoker(connection);
        }
    }
    public void dispatchToInvoker(Socket connectionClient){
        invoker.setConnectionClient(connectionClient);
        invoker.run();
    }

}
