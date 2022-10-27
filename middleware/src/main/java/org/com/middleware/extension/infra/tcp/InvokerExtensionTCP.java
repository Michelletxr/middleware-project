package org.com.middleware.extension.infra.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.com.middleware.interfaces.Iinvoker;
import org.com.middleware.extension.infra.model.AbstratInvokerExtension;
import org.com.middleware.messager.Marshaller;
import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;


public class InvokerExtensionTCP extends AbstratInvokerExtension implements Iinvoker {
  private Socket conn;
  public void setConnection(Socket conn){
      this.conn = conn;
  }
  @Override
  public RequestMessage receiveRequest() {
      RequestMessage request = new RequestMessage();
      Marshaller marshaller = new Marshaller();
      try {
          BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          request = marshaller.unMarchall(in);
      } catch (IOException e) {
          System.err.println("erro ao tentar receber mensagem: " + e.getStackTrace());
      }

      return request;
  }

  @Override
  public void sendResponse(ResponseMessage responseMessager) {
    try {
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        String responseSerializer = Marshaller.marshall(responseMessager);
        out.writeBytes(responseSerializer + "\r\n");
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

