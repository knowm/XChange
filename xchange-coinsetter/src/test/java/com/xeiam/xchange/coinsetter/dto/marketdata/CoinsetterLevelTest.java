package com.xeiam.xchange.coinsetter.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;

import org.junit.Test;

import com.google.gson.Gson;

public class CoinsetterLevelTest {

  private final Gson gson = new Gson();

  @Test
  public void testCoinsetterLevelSmart() throws IOException {

    CoinsetterLevel coinsetterLevel = getCoinsetterLevel("level-SMART.json");
    assertEquals("ASK", coinsetterLevel.getSide());
    assertEquals(49233, coinsetterLevel.getLevel());
    assertEquals(new BigDecimal("2.35"), coinsetterLevel.getSize());
    assertEquals("SMART", coinsetterLevel.getExchangeId());
    assertEquals(1398287639477193L, coinsetterLevel.getTimeStamp());
    assertEquals(95330, coinsetterLevel.getSequenceNumber());
  }

  @Test
  public void testCoinsetterLevelCoinsetter() throws IOException {

    CoinsetterLevel coinsetterLevel = getCoinsetterLevel("level-Coinsetter.json");
    assertEquals("ASK", coinsetterLevel.getSide());
    assertEquals(49233, coinsetterLevel.getLevel());
    assertEquals(new BigDecimal("0"), coinsetterLevel.getSize());
    assertEquals("COINSETTER", coinsetterLevel.getExchangeId());
    assertEquals(1398287639477193L, coinsetterLevel.getTimeStamp());
    assertEquals(5760, coinsetterLevel.getSequenceNumber());
  }

  private CoinsetterLevel getCoinsetterLevel(String resource) throws IOException {

    InputStream inputStream = getClass().getResourceAsStream(resource);
    InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
    try {
      CoinsetterLevel coinsetterLevel = gson.fromJson(reader, CoinsetterLevel.class);
      return coinsetterLevel;
    } finally {
      reader.close();
      inputStream.close();
    }
  }

}
