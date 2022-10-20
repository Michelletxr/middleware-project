package org.com.middleware.identification;

import static org.com.middleware.basic.RemoteObject.deleteMethods;
import static org.com.middleware.basic.RemoteObject.getMethods;
import static org.com.middleware.basic.RemoteObject.postMethods;
import static org.com.middleware.basic.RemoteObject.putMethods;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import org.com.middleware.annotations.DeleteMapping;
import org.com.middleware.annotations.GetMapping;
import org.com.middleware.annotations.PostMapping;
import org.com.middleware.annotations.PutMapping;
import org.com.middleware.basic.RemoteObject;
import org.com.middleware.basic.ServerRequestHandler;

public class MyMiddleware {

  private RemoteObject rmi;
  private Object stub;
  private Registry registry;

  public MyMiddleware() {
    this.registry = Registry.getInstance();
  }

  public void createRMI(Object object) {
    stub = object;
    this.rmi = new RemoteObject();
    this.addClassMethods();
    registry.bind(stub.getClass().getName(), rmi);
  }

  public void addClassMethods() {

    Class<?> objectClass = stub.getClass();
    Method[] declaredMethods = objectClass.getDeclaredMethods();

    for (Method method : declaredMethods) {
      System.out.println("add class method :" + method.toString());
      this.anotationMethodCheck(method);
    }
  }

  private void anotationMethodCheck(Method method) {
    checkMethodType(method, GetMapping.class, getMethods, method.getAnnotation(GetMapping.class).value());
    checkMethodType(method, PostMapping.class, postMethods, method.getAnnotation(PostMapping.class).value());
    checkMethodType(method, PutMapping.class, putMethods, method.getAnnotation(PutMapping.class).value());
    checkMethodType(method, DeleteMapping.class, deleteMethods, method.getAnnotation(DeleteMapping.class).value());
  }

  private void checkMethodType(Method method, Class<? extends Annotation> mappingClass,
      HashMap<String, Method> methodMap,
      String annotationValue) {
    if (method.isAnnotationPresent(mappingClass)) {
      method.setAccessible(true);
      methodMap.put(annotationValue, method);
      if (method.isAnnotationPresent(PostMapping.class)) {
        Arrays.stream(method.getParameters()).forEach(param -> System.out.println(param.getName()));
      }
    }
  }

  public void start(int port) {
    try {
      new ServerRequestHandler(port).startServer();
    } catch (IOException e) {
      System.err.println("erro ao tentar estabelecer conexão com o servidor");
    }

  }
}
