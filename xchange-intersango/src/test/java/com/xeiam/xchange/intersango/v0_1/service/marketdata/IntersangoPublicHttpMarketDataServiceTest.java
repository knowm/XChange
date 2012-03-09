package com.xeiam.xchange.intersango.v0_1.service.marketdata;

import com.xeiam.xchange.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class IntersangoPublicHttpMarketDataServiceTest {

  @Test
  public void testGetCurrencyPairId() {

    Exchange intersango = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.intersango.v0_1.IntersangoExchange");

    IntersangoPublicHttpMarketDataService testObject = (IntersangoPublicHttpMarketDataService) intersango.getMarketDataService();

    assertEquals("1", testObject.getCurrencyPairId(SymbolPair.BTC_GBP));
    assertEquals("2", testObject.getCurrencyPairId(SymbolPair.BTC_EUR));
    assertEquals("3", testObject.getCurrencyPairId(SymbolPair.BTC_USD));
    assertEquals("4", testObject.getCurrencyPairId(new SymbolPair("BTC", "PLN")));

    try {
      testObject.getCurrencyPairId(new SymbolPair("---", "USD"));
      fail();
    } catch (NotAvailableFromExchangeException e) {
      // Do nothing
    }
  }
}
