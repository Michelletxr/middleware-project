package org.com.middleware.extension;
import org.com.middleware.annotations.*;
import org.com.middleware.messager.RequestMessage;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Interceptor {
    private HashMap<String, Method> hooks;
    private static Interceptor instance;
    private String objectId;
    private Interceptor() {
        this.hooks = new HashMap<>();
    }

    public static Interceptor getInstance(){
        if(instance == null){
            instance = new Interceptor();
        }
        return instance;
    }

    public void registerHook(Method methods[]){
        String router = "";
        for (Method method: methods) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                router = objectId.concat(method.getAnnotation(GetMapping.class).value());
                hooks.put(router, method);
            }

            if (method.isAnnotationPresent(PostMapping.class)) {
                router = objectId.concat(method.getAnnotation(PostMapping.class).value());
                hooks.put(router, method);
            }

            if (method.isAnnotationPresent(PutMapping.class)) {
                router = objectId.concat(method.getAnnotation(PutMapping.class).value());
                hooks.put(router, method);
            }

            if (method.isAnnotationPresent(DeleteMapping.class)) {
                router = objectId.concat(method.getAnnotation(DeleteMapping.class).value());
                hooks.put(router, method);
            }
        }
    }

    public String before(String operation, Object context){
        String result = null;
        if("authorization".equals(operation))
        {
           result = preRequestVerification((RequestMessage) context);
        }
        return result;
    }

    public void afther(String contexto){

        try {
            FileWriter fw = new FileWriter("logs.txt", true);
            BufferedWriter buffWrite = new BufferedWriter(fw);
            String linha = "";
            Scanner in = new Scanner(System.in);
            linha = contexto;
            buffWrite.append(linha + "\n");
            buffWrite.close();
        } catch (IOException e) {
            System.out.println("erro ao tentar gerar log");
        }
    }

    public boolean checkAuthorization(Method method, RequestMessage request){
        boolean autorized = true;
        method.setAccessible(true);
        if(method.isAnnotationPresent(Authorization.class)) {
            if(Objects.isNull(request.getAuthorization())){
                autorized = false;
            }
        }
        return autorized;
    }

    public String preRequestVerification(RequestMessage request){

        Method method = hooks.get(request.getRouter());
        String status = "error";
        if(!Objects.isNull(method)) {
            if(checkAuthorization(method, request)){
                status = "sucess";
            }
        }
        return status;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}





