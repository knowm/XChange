package org.knowm.xchange.idex;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

import java.util.List;

public class IdexExchangeIntegration {

  @Test
  public void shouldRunWithoutExceptionWhenCallGetMetadata() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(
        IdexExchange.class.getName());

    ExchangeMetaData metaData = exchange.getExchangeMetaData();
    Assert.assertNotNull(metaData);
  }

  @Test
  public void shouldRunWithoutExceptionWhenCallGetExchangeSymbols() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(
        IdexExchange.class.getName());

    List<CurrencyPair> marketCurrencyPairs = exchange.getExchangeSymbols();
    Assert.assertNotNull(marketCurrencyPairs);
  }
}
