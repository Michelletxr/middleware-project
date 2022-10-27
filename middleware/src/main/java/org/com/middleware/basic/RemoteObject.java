package org.com.middleware.basic;

import org.com.middleware.messager.RequestMessage;
import org.com.middleware.messager.ResponseMessage;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class RemoteObject {
    public HashMap<String, Method> getMethods;
    public HashMap<String, Method> postMethods;
    public HashMap<String, Method> putMethods;
    public HashMap<String, Method> deleteMethods;
    private String runPath;
    private Method runMethod;

      public RemoteObject() {
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
      }catch (Exception e){
          responseMessage.setStatusCod(500);
          responseMessage.setResponseBody("erro na chamada do método: " + e.getStackTrace());
          System.err.println("erro ao tentar invocar metodo: "  + e);
      }
      return responseMessage;
    }

    private void selectMethod(String method, String router) {
        if (method.equals("GET")) {
          runMethod = getMethods.get(router);
        } else if (method.equals("POST")) {
          runMethod = postMethods.get(router);
        } else if (method.equals("DELETE")) {
          runMethod = deleteMethods.get(router);
        } else if (method.equals("PUT")) {
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
          responseObj = runMethod.invoke(instance, null);
      } else {
          //com parametros
          for (Annotation[] annotationArray : annotations) {
              for (Annotation annotation : annotationArray) {
                  String annotationName = annotation.toString();
                  if (annotationName.contains("RequestBody")) {
                      responseObj = runMethod.invoke(instance, requestMesseger);
                  }
              }
          }
      }

      return responseObj;

  }

}
