package org.com.middleware.messager;


import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;

@Data
@Builder
public class RequestMessage {
    String method;
    String router;
    String valorBody;
    JSONObject body;

    public RequestMessage(String method, String router, String valorBody, JSONObject body) {
        this.method = method;
        this.router = router;
        this.valorBody = valorBody;
        this.body = body;
    }

    public RequestMessage(String method, String router, String valorBody) {
        this.method = method;
        this.router = router;
        this.valorBody = valorBody;
    }
}

