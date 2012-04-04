package com.xeiam.xchange.intersango.v0_1.service.trade;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.trade.AccountInfo;
import com.xeiam.xchange.utils.HttpTemplate;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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
          return objectMapper.readValue(is,returnType);
        } catch (IOException e) {
          return null;
        }
      }
    });

    AccountInfo accountInfo=testObject.getAccountInfo();
    assertEquals("Unexpected number of wallets", 5, accountInfo.getWallets().size());
    
  }


}
