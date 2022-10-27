package org.com.middleware.messager;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestMessage {
    private String method;
    private String router;
    private JSONObject body;
    private String valueBody;
    private String authorization;
    private String key;
}

