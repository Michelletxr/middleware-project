package org.com.middleware.Identification;

import org.com.middleware.annotations.*;
import org.com.middleware.basic.RemoteObject;
import org.com.middleware.basic.ServerRequestHandler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MyMiddleware {
    private RemoteObject rmi;
    private Object stub;
    private Registry registry;

    private String objectId;

    public MyMiddleware() {
        this.rmi = new RemoteObject();
        this.registry = Registry.getInstance();
    }

    public void createRMI(Object object){
        stub = object;
        this.rmi = new RemoteObject();
        this.addClassMethods();
        registry.bind(objectId, rmi);
    }

    public void addClassMethods(){

        Class<?> clazs = stub.getClass();
        objectId =  stub.getClass().getAnnotation(ResquestMapping.class).value();
        Method methods[] = clazs.getDeclaredMethods();

        for (Method method: methods) {

            System.out.println("add class method :" + method.toString());

            method.setAccessible(true);

            if(method.isAnnotationPresent(GetMapping.class)){
                rmi.getMethods.put(method.getAnnotation(GetMapping.class).value(), method);
                System.out.println(" method GET router: " + method.getAnnotation(GetMapping.class).value());
            }

            if(method.isAnnotationPresent(PostMapping.class)){
                rmi.postMethods.put(method.getAnnotation(PostMapping.class).value(), method);
                System.out.println(" method POST router: " + method.getAnnotation(PostMapping.class).value());
                Arrays.stream(method.getParameters()).forEach(param -> System.out.println(param.getName()));
            }

            if(method.isAnnotationPresent(PutMapping.class)){
                rmi.putMethods.put(method.getAnnotation(PutMapping.class).value(), method);
            }

            if(method.isAnnotationPresent(DeleteMapping.class)){
                rmi.putMethods.put(method.getAnnotation(DeleteMapping.class).value(), method);
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
