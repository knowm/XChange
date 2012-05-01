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
package com.xeiam.xchange.intersango.v0_1.service.trade;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.trade.AccountInfo;
import com.xeiam.xchange.utils.HttpTemplate;

public class IntersangoTradeServiceTest {

  @Test
  public void testGetAccountInfo() {

    ExchangeSpecification es = new ExchangeSpecification("com.xeiam.xchange.intersango.v0_1.IntersangoExchange");
    es.setApiKey("abc123");
    es.setUri("https://intersango.com");
    es.setVersion("v0.1");
    es.setHost("intersango.com");
    es.setPort(1337);

    Exchange intersango = ExchangeFactory.INSTANCE.createExchange(es);

    IntersangoTradeService testObject = (IntersangoTradeService) intersango.getTradeService();

    testObject.setHttpTemplate(new HttpTemplate() {
      @Override
      public <T> T postForJsonObject(String urlString, Class<T> returnType, String postBody, ObjectMapper objectMapper, Map<String, String> httpHeaders) {
        InputStream is = IntersangoTradeServiceTest.class.getResourceAsStream("/intersango/example-accountinfo-data.json");

        try {
          return objectMapper.readValue(is, returnType);
        } catch (IOException e) {
          return null;
        }
      }
    });

    AccountInfo accountInfo = testObject.getAccountInfo();
    assertEquals("Unexpected number of wallets", 5, accountInfo.getWallets().size());

  }

}
