package org.com.middleware.identification;
import org.com.middleware.abstracts.AbstractServerRequestHandler;

import java.net.Socket;

public class ServerRequestHandler extends AbstractServerRequestHandler {

    public ServerRequestHandler(int port){
        super(port);
    }
    public void dispatchToInvoker(Socket connectionClient){
        Invoker invoker = new Invoker(connectionClient);
        invoker.run();
    }
}