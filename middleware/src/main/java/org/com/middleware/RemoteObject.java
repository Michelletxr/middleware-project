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
    public static HashMap<String, Method> putMethods;
    public static HashMap<String, Method> deleteMethods;
    private static RemoteObject remoteObject;
    Method runMethod;



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

    public ResponseMessage invokeMethods(RequestMessage requestMesseger) {

        selectMethod(requestMesseger.getMethod(), requestMesseger.getRouter());
        try {
            JSONObject my_obj = new JSONObject();
            my_obj.put("name", "teste");
            callMethod(my_obj);
        }catch (Exception e){
            System.err.println("erro"  + e);
        }

        return null;
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
    }

    private void callMethod(JSONObject requestMesseger) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> clazz = runMethod.getDeclaringClass();
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Annotation annotations[][] = runMethod.getParameterAnnotations();
        Object responseObj = null;

        //sem parametros
        if (annotations.length == 0) {
            System.out.println("o método não possui parametros");
            responseObj = runMethod.invoke(instance, null);
        } else {
            //com parametros
            for (Annotation[] annotationArray : annotations) {
                for (Annotation annotation : annotationArray) {
                    String annotationName = annotation.toString();
                    if (annotationName.contains("PathVariable")) {
                        System.out.println("PathVariable" + annotationName);
                    }
                    if (annotationName.contains("RequestBody")) {
                        responseObj = runMethod.invoke(instance, requestMesseger);
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

    }

}
