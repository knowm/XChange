package org.knowm.xchange.mexbt.dto.account;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeXBTBalanceResponseTest {

  @Test
  public void testMeXBTBalanceResponse() throws JsonParseException, JsonMappingException, IOException {
    MeXBTBalanceResponse balance = new ObjectMapper().readValue(MeXBTBalanceResponseTest.class.getResource("balance.json"),
        MeXBTBalanceResponse.class);
    MeXBTBalance[] currencies = balance.getCurrencies();
    assertEquals(2, currencies.length);
    assertEquals("BTC", currencies[0].getName());
    assertEquals(new BigDecimal("482198.87"), currencies[0].getBalance());
    assertEquals(new BigDecimal("482056"), currencies[0].getHold());
    assertEquals(0, currencies[0].getTradeCount());
  }

}
