package org.com.middleware.basic;

import org.com.middleware.basic.messager.RequestMessage;
import org.com.middleware.basic.messager.ResponseMessage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Marshaller {
    private final String ln = (System.getProperty("line.separator"));

    public String marshall(ResponseMessage response) {
        StringBuilder responseBuffer = new StringBuilder();
        String serverHeader = "Server: localhost" + ln;
        String contentTypeHeader = "Content-Type: application/json" + ln;
        String contentLengthHeader = "Content-Length: " + response.getResponseBody().length() + ln;
        responseBuffer.append(getStatusCodText(response.getStatusCod()));
        responseBuffer.append(serverHeader);
        responseBuffer.append(contentTypeHeader);
        responseBuffer.append(contentLengthHeader);
        responseBuffer.append(ln);
        responseBuffer.append(response.getResponseBody());
        return responseBuffer.toString();
    }

    public RequestMessage unMarchall(BufferedReader in) throws IOException {
        String request = in.readLine();
        System.out.println("request " +  request);
        if(request!=null) {
            StringTokenizer tokenizer = new StringTokenizer(request);
            String method = tokenizer.nextToken();
            String router = tokenizer.nextToken();
            String body = "";

            while(true){
                System.out.println("line1:  " +  request);
                if((request = in.readLine()).isBlank()){
                    while(!(request = in.readLine()).isBlank()){
                        System.out.println("line2 :  " +  request);
                        body = body.concat(request);
                    }
                    break;
                }
            }
            System.out.println("resultado final body: " + body);
           // String body = request.substring(request.lastIndexOf("{") + 1, request.length() - 1);
            return RequestMessage.builder()
                    .method(method)
                    .router(router)
                    .valorBody(body)
                    .body(new JSONObject(body))
                    .build();
        }

        return null;
    }
    public String getStatusCodText(int statusCod) {
        if (statusCod == 200) {
            return "HTTP/1.0 200 OK" + ln;
        } else if (statusCod == 400) {
            return "HTTP/1.0 400 Bad Request" + ln;
        } else if (statusCod == 405) {
            return "HTTP/1.0 405 Method Not Allowed" + ln;
        } else if (statusCod == 404) {
            return "HTTP/1.0 404 Not Found" + ln;
        }
        return "HTTP/1.0 500 Internal Server Error" + ln;
    }

}
