package org.knowm.xchange.coinsetter.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterLastTest {

  @Test
  public void testLast() throws JsonParseException, JsonMappingException, IOException {

    CoinsetterLast last = ObjectMapperHelper.readValue(getClass().getResource("last.json"), CoinsetterLast.class);

    assertEquals(1, last.getLast().length);

    CoinsetterTrade trade = last.getLast()[0];
    assertEquals(1401393463707677L, trade.getTimeStamp());
    assertEquals(14013934013056L, trade.getTickId());
    assertEquals(new BigDecimal("34.5"), trade.getVolume());
    assertEquals(new BigDecimal("225"), trade.getVolume24());
    assertEquals(new BigDecimal("568.81"), trade.getPrice());
    assertEquals(new BigDecimal("0.01"), trade.getSize());
    assertEquals("COINSETTER", trade.getExchangeId());
  }

  @Test
  public void testLast5() throws JsonParseException, JsonMappingException, IOException {

    CoinsetterLast last = ObjectMapperHelper.readValue(getClass().getResource("last5.json"), CoinsetterLast.class);

    assertEquals(5, last.getLast().length);

    CoinsetterTrade trade = last.getLast()[0];
    assertEquals(1401395030489900L, trade.getTimeStamp());
    assertEquals(14013950304L, trade.getTickId());
    assertEquals(new BigDecimal("35.5"), trade.getVolume());
    assertEquals(new BigDecimal("501"), trade.getVolume24());
    assertEquals(new BigDecimal("568.81"), trade.getPrice());
    assertEquals(new BigDecimal("0.01"), trade.getSize());
    assertEquals("COINSETTER", trade.getExchangeId());

    trade = last.getLast()[1];
    assertEquals(1401395030470800L, trade.getTimeStamp());
    assertEquals(14013940504L, trade.getTickId());
    assertEquals(new BigDecimal("35.0"), trade.getVolume());
    assertEquals(new BigDecimal("500.5"), trade.getVolume24());
    assertEquals(new BigDecimal("568.98"), trade.getPrice());
    assertEquals(new BigDecimal("7.15"), trade.getSize());
    assertEquals("COINSETTER", trade.getExchangeId());
  }

}
