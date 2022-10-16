package org.com.middleware;

import org.com.middleware.Invoker;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRequestHandler {

    private ServerSocket serverSocket;
    private  int port;

    public ServerRequestHandler(int port){
        this.port = port;
    }

    public void startServer() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("start service in port:" + serverSocket.getLocalPort());
        while (true){

            Socket connection = serverSocket.accept();

           // BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            //System.out.println("req" + in.readLine());
            Invoker invoker = new Invoker(connection);
            invoker.run();
          //  dispatchToInvoker(connetion);
        }
    }
    public void dispatchToInvoker(Socket connectionClient){
            Invoker invoker = new Invoker(connectionClient);
            invoker.run();
    }

}
