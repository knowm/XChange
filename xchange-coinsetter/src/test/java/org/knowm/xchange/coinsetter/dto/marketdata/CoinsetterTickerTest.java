package org.knowm.xchange.coinsetter.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterTickerTest {

  @Test
  public void testCoinsetterTicker() throws JsonParseException, JsonMappingException, IOException {

    CoinsetterTicker ticker = ObjectMapperHelper.readValue(getClass().getResource("ticker.json"), CoinsetterTicker.class);

    CoinsetterTrade bid = ticker.getBid();
    CoinsetterTrade ask = ticker.getAsk();
    CoinsetterTrade last = ticker.getLast();

    assertEquals(new BigDecimal("10.0"), bid.getPrice());
    assertEquals(new BigDecimal("3.0"), bid.getSize());
    assertEquals("SMART", bid.getExchangeId());
    assertEquals(1392947181242L, bid.getTimeStamp());

    assertEquals(new BigDecimal("12.0"), ask.getPrice());
    assertEquals(new BigDecimal("2.0"), ask.getSize());
    assertEquals("SMART", bid.getExchangeId());
    assertEquals(1392947181242L, bid.getTimeStamp());

    assertEquals(new BigDecimal("10.0"), last.getPrice());
    assertEquals(new BigDecimal("1.0"), last.getSize());
    assertEquals("SMART", last.getExchangeId());
    assertEquals(1392947181242L, last.getTimeStamp());

    assertEquals(new BigDecimal("35.5"), ticker.getVolume());
    assertEquals(new BigDecimal("501"), ticker.getVolume24());
  }

}
