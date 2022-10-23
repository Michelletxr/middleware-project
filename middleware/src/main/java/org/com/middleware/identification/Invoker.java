package org.com.middleware.identification;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import org.com.middleware.abstracts.AbstractInvoker;
import org.com.middleware.basic.RemoteObject;

public class Invoker extends AbstractInvoker {

  protected Registry registry;

  public Invoker(Socket connectionClient) {
    super(connectionClient);
    registry = Registry.getInstance();
  }

  @Override
  public void implementInvoker() throws RuntimeException {
    System.out.println("Connect Invoker");
    requestMessager = receiveRequestCliente();
    if (!Objects.isNull(requestMessager)) {
      //RESOLVER KEY
      RemoteObject rmi = this.registry.lookup("/hello");
      responseMessager = rmi.invokeMethods(requestMessager);
      System.out.println(responseMessager);
      sendResponseCliente();
    }

    try {
      connectionClient.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
