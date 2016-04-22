package org.knowm.xchange.coinsetter.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import org.knowm.xchange.coinsetter.GsonHelper;

public class CoinsetterLevelTest {

  @Test
  public void testCoinsetterLevelSmart() throws IOException {

    CoinsetterLevel coinsetterLevel = GsonHelper.fromJson(getClass().getResource("level-SMART.json"), CoinsetterLevel.class);
    assertEquals("ASK", coinsetterLevel.getSide());
    assertEquals(49233, coinsetterLevel.getLevel());
    assertEquals(new BigDecimal("2.35"), coinsetterLevel.getSize());
    assertEquals("SMART", coinsetterLevel.getExchangeId());
    assertEquals(1398287639477193L, coinsetterLevel.getTimeStamp());
    assertEquals(95330, coinsetterLevel.getSequenceNumber());
  }

  @Test
  public void testCoinsetterLevelCoinsetter() throws IOException {

    CoinsetterLevel coinsetterLevel = GsonHelper.fromJson(getClass().getResource("level-Coinsetter.json"), CoinsetterLevel.class);
    assertEquals("ASK", coinsetterLevel.getSide());
    assertEquals(49233, coinsetterLevel.getLevel());
    assertEquals(new BigDecimal("0"), coinsetterLevel.getSize());
    assertEquals("COINSETTER", coinsetterLevel.getExchangeId());
    assertEquals(1398287639477193L, coinsetterLevel.getTimeStamp());
    assertEquals(5760, coinsetterLevel.getSequenceNumber());
  }

}
