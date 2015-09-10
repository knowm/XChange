package com.xeiam.xchange.coinbaseex;

import org.junit.Test;

import com.xeiam.xchange.ExchangeFactory;

public class CoinbaseExExchangeTest {

  @Test
  public void coinbaseShouldBeInstantiatedWithoutAnExceptionWhenUsingDefaultSpecification() {
    ExchangeFactory.INSTANCE.createExchange(CoinbaseExExchange.class.getCanonicalName());
  }
}
