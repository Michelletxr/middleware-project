package org.com;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRequestHandler {

    private ServerSocket serverSocket;
    private  int port;

    public void startServer() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("start service in port:" + serverSocket.getLocalPort());

        while (true){
            Socket connetion = serverSocket.accept();
            dispatchToInvoker(connetion);
        }
    }
    public void dispatchToInvoker(Socket connectionClient){
            new Invoker(connectionClient);
    }

}
