package org.com.aplication;

import org.com.middleware.basic.MyMiddleware;
import org.com.middleware.extension.infra.model.Protocol;

public class Broker {


    public static void main(String[] args) {
        MyMiddleware myMiddleware = new MyMiddleware();
        Calculadora calculator = new Calculadora();
        myMiddleware.registerRMI(calculator);
        //myMiddleware.defineProtocol(Protocol.UDP);
        myMiddleware.start(8080);

    }

}
