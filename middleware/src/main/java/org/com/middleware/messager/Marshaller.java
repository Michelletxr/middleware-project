package org.com.middleware.messager;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import org.json.JSONObject;

public class Marshaller {

  private static final String LN = (System.getProperty("line.separator"));


  private Marshaller() {
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
    System.out.println("TOKEN " + token);
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
    if (request != null) {
      StringTokenizer tokenizer = new StringTokenizer(request);
      String method = tokenizer.nextToken();
      String router = tokenizer.nextToken();
      String body = "";
      String authorization = "";

      while (true) {
        request = tokenizer.nextToken();

        if (request.contains("Authorization")) {
          authorization = getAuthorization(request);
        }

        if (request.isBlank()) {
          while (!(request = tokenizer.nextToken()).isBlank()) {
            System.out.println("line2 :  " + request);
            body = body.concat(request);
          }
          break;
        }
      }
      return RequestMessage.builder()
          .method(method)
          .router(router)
          .valorBody(body)
          .body(new JSONObject(body))
          .authorization(authorization)
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
      String body = "";
      String authorization = "";

      request = in.readLine();
      if (request != null && request.contains("Authorization")) {
        authorization = getAuthorization(request);
      }

      if (request != null && request.isBlank()) {
        while (!(request = in.readLine()).isBlank()) {
          System.out.println("line2 :  " + request);
          body = body.concat(request);
        }
      }

      return RequestMessage.builder()
          .method(method)
          .router(router)
          .valorBody(body)
          .body(!"".equals(body) ? new JSONObject(body) : null)
          .authorization(authorization)
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
