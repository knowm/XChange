package org.knowm.xchange.mexbt.dto.streaming;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeXBTStreamingTickerTest {

  @Test
  public void testMeXBTStreamingTicker() throws JsonParseException, JsonMappingException, IOException {
    MeXBTStreamingTicker ticker = new ObjectMapper().readValue(MeXBTStreamingTickerTest.class.getResource("ticker.json"), MeXBTStreamingTicker.class);
    assertEquals("Ticker", ticker.getMessageType());
    assertEquals("BTCMXN", ticker.getProdPair());
    assertEquals(new BigDecimal("3815.72"), ticker.getHigh());
    assertEquals(new BigDecimal("3792.91"), ticker.getLow());
    assertEquals(new BigDecimal("3793.07"), ticker.getLast());
    assertEquals(new BigDecimal("1.42696806"), ticker.getVolume());
    assertEquals(new BigDecimal("25.97118846"), ticker.getVolume24hrs());
    assertEquals(new BigDecimal("98689.879350378"), ticker.getVolume24hrsProduct2());
    assertEquals(new BigDecimal("1.42696806"), ticker.getTotal24HrQtyTraded());
    assertEquals(new BigDecimal("5424.739983869"), ticker.getTotal24HrProduct2Traded());
    assertEquals(7L, ticker.getTotal24HrNumTrades());
    assertEquals(new BigDecimal("3809.32"), ticker.getBid());
    assertEquals(new BigDecimal("3813.38"), ticker.getAsk());
    assertEquals(23L, ticker.getBuyOrderCount());
    assertEquals(18L, ticker.getSellOrderCount());
  }

}
