package org.com.middleware.extension.infraestrutura.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.com.middleware.extension.infraestrutura.AbstractInvoker;
import org.com.middleware.messager.Marshaller;
import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;


public class InvokerTCP extends AbstractInvoker {

  private final Socket conn;

  public InvokerTCP(Socket conn) {
    super(conn);
    this.conn = conn;
    System.out.println("resultado final conn " + conn);

  }

  @Override
  public void implementInvoker() {

  }


  @Override
  public RequestMessage receive() throws IOException, InterruptedException, ClassNotFoundException {
    RequestMessage request = new RequestMessage();
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      System.out.println("  request " + in);

      request = Marshaller.unMarchall(in);
      System.out.println("  request " + request);

    } catch (IOException e) {
      System.err.println("erro ao tentar receber mensagem: " + e.getStackTrace());
    }
    System.out.println("resultado final request " + request);
    return request;
  }

  @Override
  public void send(ResponseMessage responseMessager) throws IOException, InterruptedException {
    try {
      DataOutputStream out = new DataOutputStream(conn.getOutputStream());
      String responseSerializer = Marshaller.marshall(responseMessager);
      out.writeBytes(responseSerializer + "\r\n");
      System.out.println("enviado para servidor....." + responseSerializer);
    } catch (IOException e) {
      System.err.println("erro ao tenatar enviar mensagem: " + e.getStackTrace());
    }
  }

  public void close() {
    try {
      conn.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

