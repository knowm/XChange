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
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class IntersangoTradeServiceTest {


  @Test
  public void testGetAccountInfo() {

    Map<String, Object> parameters=new HashMap<String, Object>();
    parameters.put(ExchangeSpecification.API_KEY, "abc123");
    parameters.put(ExchangeSpecification.API_URI, "https://intersango.com");
    parameters.put(ExchangeSpecification.API_VERSION, "v0.1");
    ExchangeSpecification es = new ExchangeSpecification("com.xeiam.xchange.intersango.v0_1.IntersangoExchange", parameters);
    
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
