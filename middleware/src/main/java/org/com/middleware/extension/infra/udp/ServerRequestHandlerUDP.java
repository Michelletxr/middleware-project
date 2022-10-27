package org.com.middleware.extension.infra.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.com.middleware.interfaces.Iinvoker;
import org.com.middleware.interfaces.IServerRequestHandler;

public class ServerRequestHandlerUDP implements IServerRequestHandler {
  private DatagramSocket serverSocket = null;
  private DatagramPacket receivePacket = null;
  private InvokerExtensionUDP invoker;
  @Override
  public void startServer(int port, Iinvoker invoker) throws IOException {

    this.serverSocket = new DatagramSocket(port);
    this.invoker = (InvokerExtensionUDP) invoker;

    while (true) {
      byte[] receiveData = new byte[1024];
      receivePacket = new DatagramPacket(receiveData, receiveData.length);
      serverSocket.receive(receivePacket);

      dispatchToInvoker();
    }
  }

  @Override
  public void dispatchToInvoker() {
    invoker.setReceivePacket(receivePacket, serverSocket);
    invoker.run();
  }
}