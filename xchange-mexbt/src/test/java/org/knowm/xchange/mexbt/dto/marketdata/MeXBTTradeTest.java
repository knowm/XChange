package org.knowm.xchange.mexbt.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeXBTTradeTest {

  @Test
  public void testMeXBTTrade() throws JsonParseException, JsonMappingException, IOException {
    MeXBTTrade[] meXBTTrades = new ObjectMapper().readValue(MeXBTTradeTest.class.getResource("trades.json"), MeXBTTrade[].class);

    assertEquals(0L, meXBTTrades[0].getTid());
    assertEquals(1399951989L * 1000L, meXBTTrades[0].getDate().getTime());
    assertEquals(new BigDecimal("0.085913"), meXBTTrades[0].getAmount());
    assertEquals(new BigDecimal("5819.8379"), meXBTTrades[0].getPrice());

    assertEquals(1L, meXBTTrades[1].getTid());
    assertEquals(1403143708L * 1000L, meXBTTrades[1].getDate().getTime());
    assertEquals(new BigDecimal("0.25"), meXBTTrades[1].getAmount());
    assertEquals(new BigDecimal("7895.1487"), meXBTTrades[1].getPrice());
  }

}
