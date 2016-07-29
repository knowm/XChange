package org.knowm.xchange.mexbt.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeXBTTickerTest {

  @Test
  public void testMeXBTTicker() throws JsonParseException, JsonMappingException, IOException {
    MeXBTTicker meXBTTicker = new ObjectMapper().readValue(MeXBTTickerTest.class.getResource("ticker.json"), MeXBTTicker.class);

    assertEquals(new BigDecimal("3939"), meXBTTicker.getHigh());
    assertEquals(new BigDecimal("3805.65"), meXBTTicker.getLow());
    assertEquals(new BigDecimal("3874.34"), meXBTTicker.getLast());
    assertEquals(new BigDecimal("3859.74"), meXBTTicker.getBid());
    assertEquals(new BigDecimal("3873.45"), meXBTTicker.getAsk());
    assertEquals(14L, meXBTTicker.getBidCount());
    assertEquals(14L, meXBTTicker.getAskCount());
    assertEquals(new BigDecimal("0.0168"), meXBTTicker.getChange24Hour());
    assertEquals(new BigDecimal("41.58967896"), meXBTTicker.getVolume24Hour());
    assertEquals(58L, meXBTTicker.getTrades24Hour());
  }

}
