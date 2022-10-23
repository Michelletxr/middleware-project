package org.com.middleware.basic;

import java.net.Socket;
import org.com.middleware.abstracts.AbstractServerRequestHandler;

public class ServerRequestHandler extends AbstractServerRequestHandler {


  public ServerRequestHandler(int port) {
    super(port);
  }

  public void dispatchToInvoker(Socket connectionClient) {
    Invoker invoker = new Invoker(connectionClient);
    invoker.run();
  }

}
