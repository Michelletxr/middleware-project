package org.com.middleware.identification;

import org.com.middleware.basic.RemoteObject;

import java.util.HashMap;
import java.util.Objects;

public class Registry {
    private static HashMap<String, RemoteObject> remoteObjectHashMap;
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
        String key = id.replaceAll("/", "");
        remoteObjectHashMap.put(key, object);
    }

    public RemoteObject lookup(String key) throws Exception {
        RemoteObject rmi = remoteObjectHashMap.get(key);
        if(Objects.isNull(rmi)){throw new Exception("Serviço inexistente");}
        return rmi;
    }
}
