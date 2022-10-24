package org.com.middleware.Identification;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class Invoker {

   // private final Marshaller marshaller;
    private Socket connectionClient;
    private Registry registry;
    //RequestMessage requestMessager;
   // ResponseMessage responseMessager;

    public Invoker(Socket connectionClient) {
        this.connectionClient = connectionClient;
       // this.marshaller = new Marshaller();
      //  this.requestMessager = new RequestMessage();
       // this.responseMessager = new ResponseMessage();
        this.registry = Registry.getInstance();
    }

    public void run() {
        System.out.println("Connect Invoker");
        /* requestMessager = receiveRequestCliente();
        if(!Objects.isNull(requestMessager)){
           RemoteObject rmi = this.registry.lookup("key");
           responseMessager = rmi.invokeMethods(requestMessager);
           System.out.println(responseMessager);
            sendResponseCliente();
        }*/

        try {
            connectionClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
