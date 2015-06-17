package com.xeiam.xchange.coinbaseex;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.fest.assertions.api.Assertions.assertThat;

public class CoinbaseExExchangeTest {

  @Test
  public void coinbaseShouldBeInstantiatedWithoutAnExceptionWhenUsingDefaultSpecification() {
    ExchangeFactory.INSTANCE.createExchange(CoinbaseExExchange.class.getCanonicalName());
  }
}
