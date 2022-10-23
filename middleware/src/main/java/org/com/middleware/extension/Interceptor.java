package org.com.middleware.extension;
import org.com.middleware.annotations.*;
import org.com.middleware.messager.RequestMessage;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
        for (Method method: methods) {
            if(method.isAnnotationPresent(GetMapping.class)){
                hooks.put(method.getAnnotation(GetMapping.class).value(), method);
            }
            if(method.isAnnotationPresent(PostMapping.class)){
                hooks.put(method.getAnnotation(PostMapping.class).value(), method);
            }
            if(method.isAnnotationPresent(PutMapping.class)){
                hooks.put(method.getAnnotation(PutMapping.class).value(), method);
            }
            if(method.isAnnotationPresent(DeleteMapping.class)){
                hooks.put(method.getAnnotation(DeleteMapping.class).value(), method);
            }
        }
    }

    //verify authorization required
    public String before(String operation, Object context){
        String result = null;
        if("authorization".equals(operation))
        {
           result = preRequestVerification((RequestMessage) context);
        }
        return result;
    }

    //logs
    public void afther(String contexto, ArrayList<String> args){
        String mensagem = contexto.concat(" " + args.toString());
        this.generateLogs(mensagem);
    }

     private void generateLogs(String msg){
        OutputStream os = null; // nome do arquivo que será escrito
        try {
           // String fileName = System.getProperty("user.home").concat("/logs/" + objectId);
            os = new FileOutputStream(objectId);
            Writer wr = new OutputStreamWriter(os); // criação de um escritor
            BufferedWriter br = new BufferedWriter(wr); // adiciono a um escritor de buffer
            br.write(msg);
            br.newLine();
            br.close();
        } catch (Exception e) {
           System.out.println("erro ao tentar gerar arquivos de log" + e);
        }
    }

    //checkautorization
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

    //verify request router
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





