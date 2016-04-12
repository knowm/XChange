package org.knowm.xchange.mexbt.dto.account;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeXBTUserTradeResponseTest {

  @Test
  public void testMeXBTTradeResponse() throws JsonParseException, JsonMappingException, IOException {
    MeXBTTradeResponse tradeResponse = new ObjectMapper().readValue(MeXBTUserTradeResponseTest.class.getResource("trades.json"),
        MeXBTTradeResponse.class);
    assertEquals(1373525041121L, tradeResponse.getDateTimeUtc().getTime());
    assertEquals("BTCUSD", tradeResponse.getIns());
    assertEquals(0, tradeResponse.getStartIndex());
    assertEquals(10, tradeResponse.getCount());

    MeXBTUserTrade[] trades = tradeResponse.getTrades();
    assertEquals(2, trades.length);
    assertEquals(0L, trades[0].getTid());
    assertEquals(new BigDecimal("123"), trades[0].getPx());
    assertEquals(new BigDecimal("2"), trades[0].getQty());
    assertEquals(1373142974499L, trades[0].getTime().getTime());
    assertEquals(0, trades[0].getIncomingOrderSide());
    assertEquals(2849L, trades[0].getIncomingServerOrderId());
    assertEquals(2804L, trades[0].getBookServerOrderId());
  }

}
