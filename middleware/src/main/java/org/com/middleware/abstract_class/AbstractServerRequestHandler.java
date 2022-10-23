package org.com.middleware.abstract_class;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServerRequestHandler {
    protected ServerSocket serverSocket;
    protected int port;
    public AbstractServerRequestHandler(int port){
        this.port = port;
    }

    public void startServer() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("start service in port:" + serverSocket.getLocalPort());
        while (true){
            Socket connection = serverSocket.accept();
            dispatchToInvoker(connection);
        }
    }

    public abstract void dispatchToInvoker(Socket connectionClient);
}
