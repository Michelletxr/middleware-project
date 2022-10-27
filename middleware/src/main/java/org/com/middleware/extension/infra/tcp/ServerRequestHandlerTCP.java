package org.com.middleware.extension.infra.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.com.middleware.interfaces.Iinvoker;
import org.com.middleware.interfaces.IServerRequestHandler;


public class ServerRequestHandlerTCP implements IServerRequestHandler {
  private ServerSocket serverSocket = null;
  private Socket connectionClient = null;
  private InvokerExtensionTCP invoker;

  public void startServer(int porta, Iinvoker  invoker) throws IOException {;
    this.serverSocket = new ServerSocket(porta);
      this.invoker = (InvokerExtensionTCP) invoker;
      while (true) {
          this.connectionClient = serverSocket.accept();
          dispatchToInvoker();
      }
  }

  public void dispatchToInvoker() {
    invoker.setConnection(connectionClient);
    invoker.run();
    invoker.close();
  }

}

