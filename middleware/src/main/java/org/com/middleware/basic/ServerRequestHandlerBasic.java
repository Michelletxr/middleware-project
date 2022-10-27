package org.com.middleware.basic;

import org.com.middleware.interfaces.Iinvoker;
import org.com.middleware.interfaces.IServerRequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRequestHandlerBasic implements IServerRequestHandler {
    private ServerSocket serverSocket;
    private InvokerBasic invoker;
    private Socket conn;

    @Override
    public void startServer(int port, Iinvoker invoker) throws IOException {

        this.serverSocket = new ServerSocket(port);
        this.invoker = (InvokerBasic) invoker;

        System.out.println("start service in port:" + serverSocket.getLocalPort());

        while (true){
            this.conn = serverSocket.accept();
            dispatchToInvoker();
        }
    }

    @Override
    public void dispatchToInvoker() {
        invoker.setConnectionClient(conn);
        invoker.run();
    }
}
