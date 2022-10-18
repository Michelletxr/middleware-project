package org.com.middleware.basic.messager;

import lombok.Data;


@Data
public class ResponseMessage {
    String responseBody;
    int statusCod;
}
