package org.knowm.xchange.coinsetter.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import org.knowm.xchange.coinsetter.GsonHelper;

public class CoinsetterLevelsTest {

  @Test
  public void testCoinsetterLevels() throws IOException {

    CoinsetterLevels coinsetterLevels = GsonHelper.fromJson(getClass().getResource("levels.json"), CoinsetterLevels.class);
    CoinsetterLevel[] levels = coinsetterLevels.getLevels();
    assertEquals(1, levels.length);
    assertEquals("ASK", levels[0].getSide());
    assertEquals(49233, levels[0].getLevel());
    assertEquals(new BigDecimal("0"), levels[0].getSize());
    assertEquals("COINSETTER", levels[0].getExchangeId());
    assertEquals(1398287639477193L, levels[0].getTimeStamp());
    assertEquals(5760, levels[0].getSequenceNumber());
  }

}
