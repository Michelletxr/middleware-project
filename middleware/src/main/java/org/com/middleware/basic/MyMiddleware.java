package org.com.middleware.basic;

import java.io.IOException;
import org.com.middleware.abstracts.AbstractMyMiddleware;
import org.com.middleware.identification.infraestrutura.IServerRequestHandler;
import org.com.middleware.identification.infraestrutura.model.Protocol;
import org.com.middleware.identification.infraestrutura.tcp.ServerRequestHandlerTCP;
import org.com.middleware.identification.infraestrutura.udp.ServerRequestHandlerUDP;

public class MyMiddleware extends AbstractMyMiddleware {

  RemoteObject remoteObject;

  IServerRequestHandler serverRequestHandler;

  public MyMiddleware(Protocol protocol) {
    serverRequestHandler = protocol.equals(Protocol.TCP) ? new ServerRequestHandlerTCP()
        : new ServerRequestHandlerUDP();
    remoteObject = RemoteObject.getInstance();
  }

  public MyMiddleware() {
    serverRequestHandler = new ServerRequestHandlerTCP();
    remoteObject = RemoteObject.getInstance();
  }

  public void start(int porta) {
    try {
      serverRequestHandler.startServer(porta);
    } catch (IOException e) {
      System.err.println("erro ao tentar estabelecer conexão com o servidor");
    }

  }
}
