package org.com.middleware;

import org.com.middleware.annotations.GetMapping;
import org.com.middleware.annotations.PostMapping;
import org.com.middleware.annotations.RequestBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class MyMiddleware {

    RemoteObject remoteObject;

    public MyMiddleware(){
        remoteObject = RemoteObject.getInstance();
    }

    //cadastrar os metodos da classe de negocio;
    public void addClassMethods(Object object){

        //pegar classe do objeto
        Class<?> clazs = object.getClass();

        //pegar metodos declarados (interfaces)
        Method methods[] = clazs.getDeclaredMethods();
        for (Method method: methods) {
            System.out.println("add class method :" + method.toString());
            //conferir o método da anotação
            if(method.isAnnotationPresent(GetMapping.class)){
                method.setAccessible(true);
                RemoteObject.getMethods.put(method.getAnnotation(GetMapping.class).value(), method);
                System.out.println(" method GET router: " + method.getAnnotation(GetMapping.class).value());
            }

            if(method.isAnnotationPresent(PostMapping.class)){
                method.setAccessible(true);
                RemoteObject.postMethods.put(method.getAnnotation(PostMapping.class).value(), method);
                System.out.println(" method POST router: " + method.getAnnotation(PostMapping.class).value());
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