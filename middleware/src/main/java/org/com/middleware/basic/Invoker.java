package org.com.middleware.basic;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import org.com.middleware.abstracts.AbstractInvoker;


public class Invoker extends AbstractInvoker {

  private RemoteObject remoteObject;

  public Invoker(Socket connectionClient) {
    super(connectionClient);
    remoteObject = RemoteObject.getInstance();
  }

  @Override
  public void implementInvoker() throws RuntimeException {
    System.out.println("Connect Invoker");
    requestMessager = receiveRequestCliente();
    if (!Objects.isNull(requestMessager)) {
      responseMessager = this.remoteObject.invokeMethods(requestMessager);
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
