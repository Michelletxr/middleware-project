package org.com.middleware.basic;

import org.com.middleware.abstracts.AbstractMyMiddleware;

import java.io.IOException;

public class MyMiddleware extends AbstractMyMiddleware {

  RemoteObject remoteObject;

  public MyMiddleware() {
    remoteObject = RemoteObject.getInstance();
  }

  public void start(int port) {
    try {
      new ServerRequestHandler(port).startServer();
    } catch (IOException e) {
      System.err.println("erro ao tentar estabelecer conexão com o servidor");
    }

  }
}
