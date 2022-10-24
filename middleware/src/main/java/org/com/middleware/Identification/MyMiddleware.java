package org.com.middleware.Identification;

import org.com.middleware.RemoteObject;
import org.com.middleware.ServerRequestHandler;
import org.com.middleware.annotations.DeleteMapping;
import org.com.middleware.annotations.GetMapping;
import org.com.middleware.annotations.PostMapping;
import org.com.middleware.annotations.PutMapping;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MyMiddleware {
    private RemoteObject rmi;
    private Object stub;
    private Registry registry;

    public MyMiddleware() {
        this.registry = Registry.getInstance();
    }

    public void createRMI(Object object){
        stub = object;
        this.rmi = new RemoteObject();
        this.addClassMethods();
        registry.bind(stub.getClass().getName(), rmi);
    }

    public void addClassMethods(){

        //pegar classe do objeto
        Class<?> clazs = stub.getClass();

        //pegar metodos declarados (interfaces)
        Method methods[] = clazs.getDeclaredMethods();
        for (Method method: methods) {
            System.out.println("add class method :" + method.toString());
            //conferir o método da anotação
            if(method.isAnnotationPresent(GetMapping.class)){
                method.setAccessible(true);
                rmi.getMethods.put(method.getAnnotation(GetMapping.class).value(), method);
                System.out.println(" method GET router: " + method.getAnnotation(GetMapping.class).value());
            }

            if(method.isAnnotationPresent(PostMapping.class)){
                method.setAccessible(true);
                rmi.postMethods.put(method.getAnnotation(PostMapping.class).value(), method);
                System.out.println(" method POST router: " + method.getAnnotation(PostMapping.class).value());
                Arrays.stream(method.getParameters()).forEach(param -> System.out.println(param.getName()));
            }

            if(method.isAnnotationPresent(PutMapping.class)){
                method.setAccessible(true);
                rmi.putMethods.put(method.getAnnotation(PutMapping.class).value(), method);
            }

            if(method.isAnnotationPresent(DeleteMapping.class)){
                method.setAccessible(true);
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
