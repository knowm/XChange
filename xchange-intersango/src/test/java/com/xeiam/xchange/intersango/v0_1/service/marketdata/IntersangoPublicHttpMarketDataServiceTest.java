/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.intersango.v0_1.service.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.utils.HttpTemplate;

public class IntersangoPublicHttpMarketDataServiceTest {

  @Test
  public void testGetCurrencyPairId() {

    Exchange intersango = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.intersango.v0_1.IntersangoExchange");

    IntersangoMarketDataService testObject = (IntersangoMarketDataService) intersango.getMarketDataService();

    assertEquals("1", testObject.getCurrencyPairId(CurrencyPair.BTC_GBP));
    assertEquals("2", testObject.getCurrencyPairId(CurrencyPair.BTC_EUR));
    assertEquals("3", testObject.getCurrencyPairId(CurrencyPair.BTC_USD));
    assertEquals("4", testObject.getCurrencyPairId(new CurrencyPair("BTC", "PLN")));

    try {
      testObject.getCurrencyPairId(new CurrencyPair("---", "USD"));
      fail();
    } catch (NotAvailableFromExchangeException e) {
      // Do nothing
    }
  }

  @Test
  public void testGetMarketDepth() {

    Exchange intersango = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.intersango.v0_1.IntersangoExchange");

    IntersangoMarketDataService testObject = (IntersangoMarketDataService) intersango.getMarketDataService();

    testObject.setHttpTemplate(new HttpTemplate() {
      @Override
      public <T> T getForJsonObject(String urlString, Class<T> returnType, ObjectMapper objectMapper, Map<String, String> httpHeaders) {
        InputStream is = IntersangoPublicHttpMarketDataServiceTest.class.getResourceAsStream("/intersango/example-depth-data.json");

        try {
          return objectMapper.readValue(is, returnType);
        } catch (IOException e) {
          return null;
        }
      }
    });

    testObject.getOrderBook(CurrencyPair.BTC_USD);

  }

}
