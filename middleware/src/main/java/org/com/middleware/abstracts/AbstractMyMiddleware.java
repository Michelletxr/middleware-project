package org.com.middleware.abstracts;

import org.com.middleware.annotations.*;
import org.com.middleware.basic.RemoteObject;
import java.lang.reflect.Method;

public abstract class AbstractMyMiddleware {
    protected String objectId;
    protected RemoteObject rmi;
    public void addClassMethods(Object object){
        try{
            Class<?> clazs = object.getClass();
            Method methods[] = clazs.getDeclaredMethods();
            this.objectId = getObjectId(clazs);

            for (Method method: methods) {
                method.setAccessible(true);
                String router = "";
                if (method.isAnnotationPresent(GetMapping.class)) {
                    router = objectId.concat(method.getAnnotation(GetMapping.class).value());
                    System.out.println("rota" + router);
                    rmi.getMethods.put(router, method);
                }

                if (method.isAnnotationPresent(PostMapping.class)) {
                    router = objectId.concat(method.getAnnotation(PostMapping.class).value());
                    rmi.postMethods.put(router, method);
                }

                if (method.isAnnotationPresent(PutMapping.class)) {
                    router = objectId.concat(method.getAnnotation(PutMapping.class).value());
                    rmi.putMethods.put(router, method);
                }

                if (method.isAnnotationPresent(DeleteMapping.class)) {
                    router = objectId.concat(method.getAnnotation(DeleteMapping.class).value());
                    rmi.putMethods.put(router, method);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private String getObjectId(Class<?> clazz) throws Exception{
        if(clazz.isAnnotationPresent(RequestMapping.class)){
           return clazz.getAnnotation(RequestMapping.class).value();
        }else {
            throw new Exception("não foi possível identificar o obejo id");
        }
    }
    public abstract void start(int port);
}
