package org.com.middleware.extension;
import org.com.middleware.abstract_class.AbstractInvoker;
import org.com.middleware.basic.RemoteObject;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Invoker extends AbstractInvoker {
    private Interceptor interceptor;
    private final RemoteObject remoteObject;
    public Invoker(Socket connectionClient) {
        super(connectionClient);
        this.remoteObject = RemoteObject.getInstance();
        this.interceptor = Interceptor.getInstance();
    }

    @Override
    public void implementInvoker() {

        requestMessager = receiveRequestCliente();
        if(!Objects.isNull(requestMessager)) {
            if (interceptor.before("authorization", requestMessager).equals("sucess")) {
                responseMessager = this.remoteObject.invokeMethods(requestMessager);
            }else{
                responseMessager.setResponseBody("não autorizado");
                responseMessager.setStatusCod(400);
            }
            ArrayList<String> args = new ArrayList<>();
            args.add(responseMessager.getResponseBody());
            args.add(String.valueOf((responseMessager.getStatusCod())));
            interceptor.afther("Gerar response", args);
            System.out.println("response " + responseMessager);
            sendResponseCliente();
        }

        try {
            connectionClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
