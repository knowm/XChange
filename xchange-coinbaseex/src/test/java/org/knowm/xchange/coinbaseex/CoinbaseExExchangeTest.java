package org.knowm.xchange.coinbaseex;

import org.junit.Test;

import org.knowm.xchange.ExchangeFactory;

public class CoinbaseExExchangeTest {

  @Test
  public void coinbaseShouldBeInstantiatedWithoutAnExceptionWhenUsingDefaultSpecification() {
    ExchangeFactory.INSTANCE.createExchange(CoinbaseExExchange.class.getCanonicalName());
  }
}
