package org.com.middleware.identification.infraestrutura.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.com.middleware.identification.infraestrutura.AbstractInvoker;
import org.com.middleware.messager.Marshaller;
import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;

public class InvokerUDP extends AbstractInvoker {

  private DatagramPacket receivePacket;
  private DatagramSocket datagramSocket;

  public InvokerUDP(DatagramPacket receivePacket, DatagramSocket datagramSocket) {
    this.receivePacket = receivePacket;
  }

  public RequestMessage receive() throws IOException {
    return Marshaller.unMarchall(receivePacket.getData().toString());
  }

  @Override
  public void close() {

  }

  public void send(ResponseMessage responseMessager) throws IOException, InterruptedException {
    InetAddress ipAddress = receivePacket.getAddress();
    String responseSerializer = Marshaller.marshall(responseMessager);
    int port = receivePacket.getPort();
    byte[] sendData = responseSerializer.getBytes();
    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
    datagramSocket.send(sendPacket);
  }
}