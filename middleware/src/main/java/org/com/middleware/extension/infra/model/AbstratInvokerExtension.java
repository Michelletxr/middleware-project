package org.com.middleware.extension.infra.model;

import org.com.middleware.extension.Interceptor;
import org.com.middleware.identification.Registry;
import org.com.middleware.interfaces.Iinvoker;
import org.com.middleware.basic.RemoteObject;
import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;

import java.util.Objects;

public abstract class AbstratInvokerExtension implements Runnable, Iinvoker {

    protected RequestMessage requestMessager;
    protected ResponseMessage responseMessager;
    protected Interceptor interceptor;
    protected   RemoteObject remoteObject;

    protected Registry registry;

    public AbstratInvokerExtension(){
        this.interceptor = Interceptor.getInstance();
        this.registry = Registry.getInstance();
    }


    @Override
    public void run() {
        implementInvoker();
    }
    public void implementInvoker() {
        requestMessager = receiveRequest();
        if (!Objects.isNull(requestMessager)) {
            try {

                if (interceptor.before("authorization", requestMessager).equals("sucess")) {
                    this.remoteObject = this.registry.lookup(requestMessager.getKey());
                    responseMessager = this.remoteObject.invokeMethods(requestMessager);
                } else {
                    System.out.println("nao autorizado");
                    responseMessager.setResponseBody("não autorizado");
                    responseMessager.setStatusCod(400);
                }

            } catch(Exception e) {
                responseMessager.setStatusCod(500);
                responseMessager.setResponseBody(e.getMessage());
            }
            interceptor.afther("Geração de response com status: " + responseMessager.getStatusCod());
            sendResponse(responseMessager);
        }
    }

    public abstract RequestMessage receiveRequest();

    public abstract void sendResponse(ResponseMessage responseMessager);


}
