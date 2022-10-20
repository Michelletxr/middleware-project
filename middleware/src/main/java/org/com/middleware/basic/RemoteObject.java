package org.com.middleware.basic;

import org.com.middleware.basic.messager.RequestMessage;
import org.com.middleware.basic.messager.ResponseMessage;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class RemoteObject {

    public static HashMap<String, Method> getMethods;
    public static HashMap<String, Method> postMethods;
    public static HashMap<String, Method> putMethods;
    public static HashMap<String, Method> deleteMethods;

    public static ArrayList<String> autorizedRoutes;
    private static RemoteObject remoteObject;

    private String runPath;
    private Method runMethod;



    static public RemoteObject getInstance() {
        if (remoteObject == null) {
            remoteObject = new RemoteObject();
            getMethods = new HashMap<>();
            postMethods = new HashMap<>();
            putMethods = new HashMap<>();
            deleteMethods = new HashMap<>();
        }
        return remoteObject;
    }

    public RemoteObject()
    {
        getMethods = new HashMap<>();
        postMethods = new HashMap<>();
        putMethods = new HashMap<>();
        deleteMethods = new HashMap<>();
    }

    public ResponseMessage invokeMethods(RequestMessage requestMesseger) {
        ResponseMessage responseMessage = new ResponseMessage();

        selectMethod(requestMesseger.getMethod(), requestMesseger.getRouter());
        try {
            Object obj = callMethod(requestMesseger.getBody());
            responseMessage.setResponseBody(obj.toString());
            responseMessage.setStatusCod(200);
            System.out.println("obj "  + obj.toString());
        }catch (Exception e){
            responseMessage.setStatusCod(500);
            responseMessage.setResponseBody("erro: " + e);
            System.err.println("erro ao tentar invocar metodo: "  + e);
        }

        return responseMessage;
    }

    private void selectMethod(String method, String router) {
        if (method.equals("GET")) {
            runMethod = getMethods.get(router);
        } else if (method.equals("POST")){
            runMethod = postMethods.get(router);
        }else if(method.equals("DELETE")){
            runMethod = deleteMethods.get(router);
        }else if(method.equals("PUT")){
            runMethod = putMethods.get(router);
        }
        runPath = router;
    }

    private Object callMethod(JSONObject requestMesseger) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> clazz = runMethod.getDeclaringClass();
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Annotation annotations[][] = runMethod.getParameterAnnotations();
        Object responseObj = null;

        //sem parametros
        if (annotations.length == 0) {
            System.out.println("o método não possui parametros");
            responseObj = runMethod.invoke(instance, null);
            System.out.println("response: " + responseObj);
        } else {

            //com parametros
            for (Annotation[] annotationArray : annotations) {
                for (Annotation annotation : annotationArray) {
                    String annotationName = annotation.toString();
                    if (annotationName.contains("PathVariable"))
                    {
                        System.out.println("PathVariable" + annotationName);

                    }
                    if (annotationName.contains("RequestBody")) {
                        responseObj = runMethod.invoke(instance, requestMesseger);
                        System.out.println("response: " + responseObj);
                       /* for ( Class<?> param :runMethod.getParameterTypes())
                        {
                            //Object objectParam = param.getDeclaredConstructor();
                            //System.out.println(objectParam);
                            //runMethod.invoke(instance, objectParam); //desserialize object
                        }
                        */
                    }
                }
            }
        }
        return responseObj;

    }

}
