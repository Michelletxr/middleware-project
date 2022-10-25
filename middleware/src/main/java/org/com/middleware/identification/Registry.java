package org.com.middleware.identification;

import org.com.middleware.basic.RemoteObject;

import java.util.HashMap;

public class Registry {

    public  static  HashMap<String, RemoteObject> remoteObjectHashMap;

    private static Registry registry;

    static public Registry getInstance() {

        if (registry == null) {
            registry = new Registry();
            remoteObjectHashMap = new HashMap<>();
        }
        return registry;
    }

    public void bind(String id, RemoteObject object)
    {
        remoteObjectHashMap.put(id,object);

    }

    public RemoteObject lookup(String key){
        return remoteObjectHashMap.get(key);
    }
}
