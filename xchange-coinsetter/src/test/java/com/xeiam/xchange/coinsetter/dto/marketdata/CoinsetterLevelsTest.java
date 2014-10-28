package com.xeiam.xchange.coinsetter.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;

import org.junit.Test;

import com.google.gson.Gson;

public class CoinsetterLevelsTest {

  private final Gson gson = new Gson();

  @Test
  public void testCoinsetterLevels() throws IOException {

    CoinsetterLevels coinsetterLevels = getCoinsetterLevels("levels.json");
    CoinsetterLevel[] levels = coinsetterLevels.getLevels();
    assertEquals(1, levels.length);
    assertEquals("ASK", levels[0].getSide());
    assertEquals(49233, levels[0].getLevel());
    assertEquals(new BigDecimal("0"), levels[0].getSize());
    assertEquals("COINSETTER", levels[0].getExchangeId());
    assertEquals(1398287639477193L, levels[0].getTimeStamp());
    assertEquals(5760, levels[0].getSequenceNumber());
  }

  private CoinsetterLevels getCoinsetterLevels(String resource) throws IOException {

    InputStream inputStream = getClass().getResourceAsStream(resource);
    InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
    try {
      CoinsetterLevels coinsetterLevels = gson.fromJson(reader, CoinsetterLevels.class);
      return coinsetterLevels;
    } finally {
      reader.close();
      inputStream.close();
    }
  }

}
