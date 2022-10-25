package org.com.middleware.extension.infraestrutura.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.com.middleware.extension.infraestrutura.IServerRequestHandler;


public class ServerRequestHandlerTCP implements IServerRequestHandler {

  private ServerSocket serverSocket = null;
  private Socket conn = null;

  public void startServer(int porta) throws IOException {
    this.serverSocket = new ServerSocket(porta);
    System.out.println("start service in port:" + serverSocket.getLocalPort());
    while (true) {
      conn = serverSocket.accept();
      dispatchToInvoker();
    }
  }

  public void dispatchToInvoker() {
    InvokerTCP invoker = new InvokerTCP(conn);
    invoker.run();
  }

}

