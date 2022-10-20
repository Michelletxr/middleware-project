package org.com.middleware.extension;
import org.com.middleware.annotations.*;
import org.com.middleware.basic.Marshaller;
import org.com.middleware.basic.messager.RequestMessage;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;

public class Interceptor {

    private HashMap<String, Method> hooks;
    private Marshaller marshaller;

    private HashMap<String, Object> context;

    public void registerHook(Method methods[]){
        for (Method method: methods) {
            hooks.put(method.getAnnotation(GetMapping.class).value(), method);
            hooks.put(method.getAnnotation(PostMapping.class).value(), method);
            hooks.put(method.getAnnotation(PutMapping.class).value(), method);
            hooks.put(method.getAnnotation(DeleteMapping.class).value(), method);
        }
    }

    //verify autorization required
    //intercepter context

    public String before(String operation, Object context){

        if("autorization".equals(operation))
        {
            preRequestVerification((RequestMessage) context);
        }

        return null;
    }

    //imprime logs
    public void afther(){}

    public boolean checkAutorization(Method method, RequestMessage request){
        boolean autorized = true;
        if(method.isAnnotationPresent(Autorized.class)) {
            if(Objects.isNull(marshaller.getAutorization(request))){
                        autorized = false;
            }
        }

        return autorized;
    }

    //verifica a requisição
    //verifica se a rota existe
    //verifica se o metodo requisitado precisa de autorização (em caso verdadeiro verifica cabeçalho e token)
    public String preRequestVerification(RequestMessage request){

        Method method = hooks.get(request.getRouter());
        String status = "error";
        if(!Objects.isNull(method)) {
                if(checkAutorization(method, request)){
                    status = "sucess";
                }
        }

        return status;

    }

}





