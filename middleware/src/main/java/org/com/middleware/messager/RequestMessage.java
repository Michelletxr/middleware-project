package org.com.middleware.messager;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;

@Data
@Builder
@AllArgsConstructor
public class RequestMessage {
    private String method;
    private String router;
    private String valorBody;
    private JSONObject body;

}

