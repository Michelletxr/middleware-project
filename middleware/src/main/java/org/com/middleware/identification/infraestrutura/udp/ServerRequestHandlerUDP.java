package org.com.middleware.identification.infraestrutura.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import org.com.middleware.identification.infraestrutura.IServerRequestHandler;

public class ServerRequestHandlerUDP implements IServerRequestHandler {

  private DatagramSocket serverSocket = null;
  private DatagramPacket receivePacket = null;

  @Override
  public void startServer(int porta) throws IOException {
    this.serverSocket = new DatagramSocket(porta);
    System.out.println("start service in port:" + serverSocket.getLocalPort());
    while (true) {
      byte[] receiveData = new byte[1024];
      receivePacket = new DatagramPacket(receiveData, receiveData.length);
      serverSocket.receive(receivePacket);
      dispatchToInvoker();
    }
  }

  @Override
  public void dispatchToInvoker() {
    InvokerUDP invoker = new InvokerUDP(receivePacket, serverSocket);
    invoker.run();
  }
}