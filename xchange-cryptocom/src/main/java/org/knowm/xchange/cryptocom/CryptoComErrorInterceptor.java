package org.knowm.xchange.cryptocom;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.knowm.xchange.cryptocom.dto.CryptoComException;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;
import si.mazi.rescu.Interceptor;

/**
 * Converts every Crypto.com-specific error - a {@code 200 OK} envelope with a non-zero {@code
 * code}, or a non-2xx HTTP status deserialized into a {@link CryptoComException} - into the
 * matching XChange {@link org.knowm.xchange.exceptions.ExchangeException} here, at the single choke
 * point every REST call passes through, so no caller (raw or otherwise) ever sees the
 * exchange-specific exception type.
 */
public class CryptoComErrorInterceptor implements Interceptor {

  @Override
  public Object aroundInvoke(
      InvocationHandler invocationHandler, Object proxy, Method method, Object[] args)
      throws Throwable {
    Object result;
    try {
      result = invocationHandler.invoke(proxy, method, args);
    } catch (CryptoComException e) {
      throw CryptoComErrorAdapter.adaptError(e);
    }

    if (result instanceof CryptoComResponse) {
      CryptoComResponse response = (CryptoComResponse) result;
      if (response.getCode() != 0) {
        throw CryptoComErrorAdapter.adaptError(response);
      }
    }

    return result;
  }
}
