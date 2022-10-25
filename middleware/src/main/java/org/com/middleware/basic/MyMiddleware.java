package org.com.middleware.basic;

import java.io.IOException;
import java.lang.reflect.Method;

import org.com.middleware.identification.Registry;
import org.com.middleware.abstracts.AbstractInvoker;
import org.com.middleware.annotations.*;

public class MyMiddleware  {
  private AbstractInvoker invoker;
  private Registry registry;
  public String objectId;
  private RemoteObject rmi;
  private Object stub;

  public MyMiddleware() {
    rmi = RemoteObject.getInstance();
  }

  public void setInvoker(AbstractInvoker invoker){
    this.invoker = invoker;
  }

  public void registerRMI(Object object) {

    this.registry = Registry.getInstance();
    this.stub = object;
    this.addClassMethods();

    registry.bind(objectId, rmi);
  }


  public void addClassMethods(){
    try{
      Class<?> clazs = stub.getClass();
      Method methods[] = clazs.getDeclaredMethods();
      this.objectId = getObjectId(clazs);

      for (Method method: methods) {
          method.setAccessible(true);
          String router = "";
          if (method.isAnnotationPresent(GetMapping.class)) {
            router = objectId.concat(method.getAnnotation(GetMapping.class).value());
            System.out.println("rota" + router);
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
            rmi.putMethods.put(router, method);
          }
        }
      }catch (Exception e){
        System.out.println(e);
    }
  }

  private String getObjectId(Class<?> clazz) throws Exception{

    if(clazz.isAnnotationPresent(RequestMapping.class)){
        return clazz.getAnnotation(RequestMapping.class).value();
    }else {
        throw new Exception("não foi possível identificar o obejo id");
    }
  }

  public void start(int porta) {
    try {
        new ServerRequestHandler(porta, invoker).startServer();
    } catch (IOException e) {
        System.err.println("erro ao tentar estabelecer conexão com o servidor");
    }

  }
}

