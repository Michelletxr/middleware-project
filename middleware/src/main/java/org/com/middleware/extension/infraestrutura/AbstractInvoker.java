package org.com.middleware.extension.infraestrutura;

import java.io.IOException;
import java.util.Objects;
import org.com.middleware.basic.RemoteObject;
import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;

public abstract class AbstractInvoker implements Runnable {


  private RemoteObject remoteObject;

  public AbstractInvoker() {
    remoteObject = RemoteObject.getInstance();
  }
//  protected Registry registry;
//
//  public AbstractInvoker() {
//    registry = Registry.getInstance();
//  }

  @Override
  public void run() {
    try {
      implementInvoker();
    } catch (IOException | InterruptedException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public void implementInvoker() throws RuntimeException, IOException, InterruptedException, ClassNotFoundException {
    System.out.println("Connect Invoker");
    RequestMessage requestMessager = receive();
    if (!Objects.isNull(requestMessager)) {
      ResponseMessage responseMessager = this.remoteObject.invokeMethods(requestMessager);
      System.out.println(responseMessager);
      send(responseMessager);
    }

    close();
  }

//  public void implementInvoker() throws RuntimeException, IOException, InterruptedException, ClassNotFoundException {
//    System.out.println("Connect Invoker");
//    RequestMessage requestMessager = receive();
//    System.out.println(requestMessager.toString() + "jjijo");
//
//    if (!Objects.isNull(requestMessager)) {
//      //RESOLVER KEY
//      RemoteObject rmi = this.registry.lookup("/hello");
//      ResponseMessage responseMessager = rmi.invokeMethods(requestMessager);
//      System.out.println(responseMessager);
//      send(responseMessager);
//    }
//  }


  //  @Override
//  public void implementInvoker() {
//
//    requestMessager = receiveRequestCliente();
//    if (!Objects.isNull(requestMessager)) {
//      if (interceptor.before("authorization", requestMessager).equals("sucess")) {
//        responseMessager = this.remoteObject.invokeMethods(requestMessager);
//      } else {
//        responseMessager.setResponseBody("não autorizado");
//        responseMessager.setStatusCod(400);
//      }
//      ArrayList<String> args = new ArrayList<>();
//      args.add(responseMessager.getResponseBody());
//      args.add(String.valueOf((responseMessager.getStatusCod())));
//      interceptor.afther("Gerar response", args);
//      System.out.println("response " + responseMessager);
//      sendResponseCliente();
//    }
//
//    try {
//      connectionClient.close();
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//
//  }
  public abstract void send(ResponseMessage responseMessager) throws IOException, InterruptedException;

  public abstract RequestMessage receive() throws IOException, InterruptedException, ClassNotFoundException;

  public abstract void close();
}
