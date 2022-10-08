package org.com.middleware;

import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class RemoteObject {

    public static HashMap<String, Method> getMethods;
    public static HashMap<String, Method> postMethods;
    private static RemoteObject remoteObject;
    Method runMethod;

    static public RemoteObject getInstance() {
        if (remoteObject == null) {
            remoteObject = new RemoteObject();
            getMethods = new HashMap<>();
            postMethods = new HashMap<>();
        }
        return remoteObject;
    }

    public ResponseMessage invokeMethods(RequestMessage requestMesseger) {

        selectMethod(requestMesseger.getMethod(), requestMesseger.getRouter());
        System.out.println("Invocando metodo " + runMethod.toString());
        try {
            JSONObject my_obj = new JSONObject();
            my_obj.put("name", "teste");
            //System.out.println("body" + requestMesseger.getBody());
            callMethod(my_obj);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }

        return null;
    }

    private void selectMethod(String method, String router) {

        if (method.equals("GET")) {
            runMethod = getMethods.get(router);

        } else if (method.equals("POST")) {

            runMethod = postMethods.get(router);

        }
    }


    //passa os parametros para o método
    private void callMethod(JSONObject requestMesseger) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> clazz = runMethod.getDeclaringClass();
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Annotation[][] annotations = runMethod.getParameterAnnotations();
        Class<?>[] parameters = runMethod.getParameterTypes();

        System.out.println(parameters[0].arrayType());
        //parse json para tipo de objeto

        for (Annotation[] annotationArray : annotations) {
            for (Annotation annotation : annotationArray) {
                System.out.println("anotação do parametro: " + annotation.toString());
            }
        }
    }
}
