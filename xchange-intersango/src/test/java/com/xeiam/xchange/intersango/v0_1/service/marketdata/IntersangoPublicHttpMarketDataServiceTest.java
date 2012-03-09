package com.xeiam.xchange.intersango.v0_1.service.marketdata;

import com.xeiam.xchange.*;
import com.xeiam.xchange.utils.HttpTemplate;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

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

  @Test
  public void testGetMarketDepth() {

    Exchange intersango = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.intersango.v0_1.IntersangoExchange");

    IntersangoPublicHttpMarketDataService testObject = (IntersangoPublicHttpMarketDataService) intersango.getMarketDataService();

    testObject.setHttpTemplate(new HttpTemplate() {
      @Override
      public <T> T getForJsonObject(String urlString, Class<T> returnType, ObjectMapper objectMapper, Map<String, String> httpHeaders) {
        InputStream is = IntersangoPublicHttpMarketDataServiceTest.class.getResourceAsStream("/intersango/example-depth-data.json");

        try {
          return objectMapper.readValue(is,returnType);
        } catch (IOException e) {
          return null;
        }
      }
    });

    testObject.getOrderBook(SymbolPair.BTC_USD);
    
  }


}
