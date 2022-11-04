package org.com.middleware.basic;

import java.io.IOException;
import java.lang.reflect.Method;

import org.com.middleware.interfaces.Iinvoker;
import org.com.middleware.interfaces.IServerRequestHandler;
import org.com.middleware.extension.infra.model.Protocol;
import org.com.middleware.extension.infra.tcp.InvokerExtensionTCP;
import org.com.middleware.extension.infra.tcp.ServerRequestHandlerTCP;
import org.com.middleware.extension.infra.udp.InvokerExtensionUDP;
import org.com.middleware.extension.infra.udp.ServerRequestHandlerUDP;
import org.com.middleware.extension.Interceptor;
import org.com.middleware.identification.Registry;
import org.com.middleware.annotations.*;

public class MyMiddleware  {
  private IServerRequestHandler serverRequestHandler;
  private Iinvoker invoker;
  private Registry registry;
  private Object stub;
  private Protocol protocol;
  private String objectId;

    public static void main(String[] args) {

    }
    public MyMiddleware() {
        this.protocol = Protocol.TCP;
        this.invoker = new InvokerBasic();
        this.serverRequestHandler = new ServerRequestHandlerBasic();
        this.registry = Registry.getInstance();
    }

    public void registerRMI(Object object) {
        try{
            this.stub = object;
            this.objectId = getObjectId(stub.getClass());
            RemoteObject remoteObject = new RemoteObject();
            this.addClassMethods(stub.getClass(), remoteObject);
            registry.bind(objectId, remoteObject);
        }catch (Exception e){
            System.err.println(e);
        }
  }

    public void registerHooks(){
        Interceptor interceptor = Interceptor.getInstance();
        interceptor.setObjectId(this.objectId);
        interceptor.registerHook(this.stub.getClass().getDeclaredMethods());
        defineProtocol(protocol);
    }

    public void defineProtocol(Protocol protocol){
        this.protocol = protocol;
        serverRequestHandler = protocol.equals(Protocol.TCP) ? new ServerRequestHandlerTCP()
                : new ServerRequestHandlerUDP();

        this.chanceInvoker();
    }

    private void chanceInvoker(){
        if(protocol == Protocol.TCP){
            this.invoker = new InvokerExtensionTCP();
        }else{
            this.invoker = new InvokerExtensionUDP();
        }
    }

  private void addClassMethods(Class<?> clazs, RemoteObject rmi){
      Method methods[] = clazs.getDeclaredMethods();
      for (Method method: methods) {
          method.setAccessible(true);
          String router = "";
          if (method.isAnnotationPresent(GetMapping.class)) {
            router = objectId.concat(method.getAnnotation(GetMapping.class).value());
            rmi.getMethods.put(router, method);
          }

          if (method.isAnnotationPresent(PostMapping.class)) {
            router = objectId.concat(method.getAnnotation(PostMapping.class).value());
            rmi.postMethods.put(router, method);
          }

          if (method.isAnnotationPresent(PutMapping.class)) {
            router = objectId.concat(method.getAnnotation(PutMapping.class).value());
            rmi.putMethods.put(router, method);
          }

          if (method.isAnnotationPresent(DeleteMapping.class)) {
            router = objectId.concat(method.getAnnotation(DeleteMapping.class).value());
            rmi.deleteMethods.put(router, method);
          }
        }
  }

  private String getObjectId(Class<?> clazz) throws Exception{
        if(clazz.isAnnotationPresent(RequestMapping.class)){
            return clazz.getAnnotation(RequestMapping.class).value();
        }else {
            throw new Exception("não foi possível identificar o id");
        }
  }

  public void start(int port) {
    try {
        this.serverRequestHandler.startServer(port, this.invoker);
    } catch (IOException e) {
        System.err.println("erro ao tentar estabelecer conexão com o servidor");
    }

  }
}

