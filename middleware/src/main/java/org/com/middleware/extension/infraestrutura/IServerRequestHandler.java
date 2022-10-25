package org.com.middleware.extension.infraestrutura;

import java.io.IOException;

public interface IServerRequestHandler {

  public void startServer(int porta) throws IOException;

  public void dispatchToInvoker();

}
