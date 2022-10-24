package org.com.middleware.extension;

import org.com.middleware.abstracts.AbstractMyMiddleware;
import org.com.middleware.basic.RemoteObject;

import java.io.IOException;
import org.com.middleware.identification.infraestrutura.tcp.ServerRequestHandlerTCP;

public class MyMiddleware extends AbstractMyMiddleware {

  private Interceptor interceptor;

  public MyMiddleware() {
    this.rmi = RemoteObject.getInstance();
    interceptor = Interceptor.getInstance();
  }

  public void start(int port) {
    try {
      interceptor.setObjectId(this.objectId);
      new ServerRequestHandlerTCP().startServer(port);
    } catch (IOException e) {
      System.err.println("erro ao tentar estabelecer conexão com o servidor");
    }

  }
}
