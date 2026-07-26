package org.knowm.xchange.cryptocom;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;
import si.mazi.rescu.Interceptor;

public class CryptoComErrorInterceptor implements Interceptor {

  @Override
  public Object aroundInvoke(
      InvocationHandler invocationHandler, Object proxy, Method method, Object[] args)
      throws Throwable {
    Object result = invocationHandler.invoke(proxy, method, args);

    if (result instanceof CryptoComResponse) {
      CryptoComResponse response = (CryptoComResponse) result;
      if (response.getCode() != 0) {
        throw CryptoComErrorAdapter.adaptError(response);
      }
    }

    return result;
  }
}
