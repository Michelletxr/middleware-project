package org.com.middleware.extension.infra.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.com.middleware.interfaces.Iinvoker;
import org.com.middleware.extension.infra.model.AbstratInvokerExtension;
import org.com.middleware.messager.Marshaller;
import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;

public class InvokerExtensionUDP extends AbstratInvokerExtension implements Iinvoker {

  private DatagramPacket receivePacket;
  private DatagramSocket datagramSocket;

  public void setReceivePacket(DatagramPacket receivePacket, DatagramSocket datagramSocket){
    this.receivePacket = receivePacket;
    this.datagramSocket = datagramSocket;
  }

  @Override
  public RequestMessage receiveRequest() {
    try {
      return Marshaller.unMarchall(new String(receivePacket.getData()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void sendResponse(ResponseMessage responseMessager) {
    InetAddress ipAddress = receivePacket.getAddress();
    String responseSerializer = Marshaller.marshall(responseMessager);
    int port = receivePacket.getPort();
    byte[] sendData = responseSerializer.getBytes();
    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
    try {
      datagramSocket.send(sendPacket);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}