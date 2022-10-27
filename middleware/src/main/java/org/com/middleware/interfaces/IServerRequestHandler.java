package org.com.middleware.interfaces;

import org.com.middleware.interfaces.Iinvoker;

import java.io.IOException;

public interface IServerRequestHandler {

  public void startServer(int port, Iinvoker invoker) throws IOException;
  public void dispatchToInvoker();


}
