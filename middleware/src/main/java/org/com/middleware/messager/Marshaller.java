package org.com.middleware.messager;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.StringTokenizer;
import org.json.JSONObject;

public class Marshaller {
    private static final String LN = (System.getProperty("line.separator"));

    public static String getRouterKey(String router){
        String key = null;
        String[] flags = router.split("/");
        if(!Objects.isNull(flags)){
            key = flags[1];
        }
        return key;
    }
    public static String getAuthorization(String str) {
        StringTokenizer tokenizer = new StringTokenizer(str);
        String token = null;
        while (tokenizer.hasMoreElements()) {
            String value = tokenizer.nextToken();
            if ("Bearer".equals(value) || "Basic".equals(value)) {
                token = tokenizer.nextToken();
            }
        }
        return token;
    }

    public static String marshall(ResponseMessage response) {
        StringBuilder responseBuffer = new StringBuilder();
        String serverHeader = "Server: localhost" + LN;
        String contentTypeHeader = "Content-Type: application/json" + LN;
        String contentLengthHeader = "Content-Length: " + response.getResponseBody().length() + LN;
        responseBuffer.append(getStatusCodText(response.getStatusCod()));
        responseBuffer.append(serverHeader);
        responseBuffer.append(contentTypeHeader);
        responseBuffer.append(contentLengthHeader);
        responseBuffer.append(LN);
        responseBuffer.append(response.getResponseBody());
        return responseBuffer.toString();
    }


    public static RequestMessage unMarchall(String request) throws IOException {
        String[] req = request.split("\n");
        if(req != null){
            StringTokenizer tokenizer = new StringTokenizer(req[0]);
            String method = tokenizer.nextToken();
            String router = tokenizer.nextToken();
            String key = getRouterKey(router);
            String body = "";
            String authorization = null;

            for (int i = 0; i < req.length ; i++) {
                if (req[i].contains("Authorization")) {authorization = getAuthorization(req[i]);}
                if(req[i].isBlank()){ body = req[++i];break;};
            }

            return RequestMessage.builder()
                    .method(method)
                    .router(router)
                    .body(new JSONObject(body))
                    .authorization(authorization)
                    .key(key)
                    .build();
        }
        return null;
    }

    public static RequestMessage unMarchall(BufferedReader in) throws IOException {
        String request = in.readLine();
        if (request != null) {
            StringTokenizer tokenizer = new StringTokenizer(request);
            String method = tokenizer.nextToken();
            String router = tokenizer.nextToken();
            String key = getRouterKey(router);
            String body = "";
            String authorization = null;
            boolean stop = false;

            while (!stop) {
                request = in.readLine();
                if (request.contains("Authorization")) {authorization = getAuthorization(request);}
                if (request.isBlank()) {
                    request = in.readLine();
                    while (!request.isBlank()) {
                        body = body + request;
                        request = in.readLine();
                    }
                    break;
                }
            }
            return RequestMessage.builder()
                    .method(method)
                    .router(router)
                    .body(!"".equals(body) ? new JSONObject(body) : null)
                    .valueBody(body)
                    .authorization(authorization)
                    .key(key)
                    .build();
        }

        return null;
    }

    private static String getStatusCodText(int statusCod) {
        if (statusCod == 200) {
            return "HTTP/1.0 200 OK" + LN;
        } else if (statusCod == 400) {
            return "HTTP/1.0 400 Bad Request" + LN;
        } else if (statusCod == 405) {
            return "HTTP/1.0 405 Method Not Allowed" + LN;
        } else if (statusCod == 404) {
            return "HTTP/1.0 404 Not Found" + LN;
        }
        return "HTTP/1.0 500 Internal Server Error" + LN;
    }

}
