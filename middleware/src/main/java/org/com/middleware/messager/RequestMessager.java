package org.com.middleware.messager;


import lombok.Data;
import netscape.javascript.JSObject;

@Data
public class RequestMessager {
    String method;
    String router;
    String valorBody;
    JSObject body;

    public RequestMessager(String method, String router,String valorBody){
        this.method = method;
        this.router = router;
        this.valorBody = valorBody;
    }
}

