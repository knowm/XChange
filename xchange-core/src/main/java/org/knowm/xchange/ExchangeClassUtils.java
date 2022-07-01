package org.knowm.xchange;

import org.knowm.xchange.exceptions.ExchangeException;

public final class ExchangeClassUtils {

  private ExchangeClassUtils() {}

  public static Class<? extends Exchange> exchangeClassForName(String exchangeClassName) {
    // Attempt to create an instance of the exchange provider
    try {
      Class<?> exchangeProviderClass = Class.forName(exchangeClassName);

      // Test that the class implements Exchange
      if (Exchange.class.isAssignableFrom(exchangeProviderClass)) {
        return (Class<? extends Exchange>) exchangeProviderClass;
      } else {
        throw new ExchangeException(
            "Class '" + exchangeClassName + "' does not implement Exchange");
      }
    } catch (ClassNotFoundException e) {
      throw new ExchangeException("Problem creating Exchange (class not found)", e);
    }
  }
}
