package org.com.application;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TesteClient {
    public static void main(String[] args) {
        try {
            Socket cliente = new Socket("localhost", 8080);
            ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
            out.writeObject("teste de conexão");

        } catch (IOException e) {
            System.err.println("erro");
        }
    }
}
