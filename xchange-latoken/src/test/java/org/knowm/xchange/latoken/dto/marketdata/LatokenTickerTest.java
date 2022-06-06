package org.knowm.xchange.latoken.dto.marketdata;

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

public class LatokenTickerTest {
  LatokenTicker ticker;

  @Before
  public void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/marketdata/latoken-ticker-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    ticker = mapper.readValue(is, LatokenTicker.class);
  }

  @Test
  public void testLatokenTicker() {
    assertNotNull(ticker);
  }

  @Test
  public void testGetPairId() {
    assertNotNull(ticker.getPairId());
  }

  @Test
  public void testGetSymbol() {
    assertNotNull(ticker.getSymbol());
  }

  @Test
  public void testGetVolume() {
    assertNotNull(ticker.getVolume());
  }

  @Test
  public void testGetOpen() {
    assertNotNull(ticker.getOpen());
  }

  @Test
  public void testGetLow() {
    assertNotNull(ticker.getLow());
  }

  @Test
  public void testGetHigh() {
    assertNotNull(ticker.getHigh());
  }

  @Test
  public void testGetClose() {
    assertNotNull(ticker.getClose());
  }

  @Test
  public void testGetPriceChange() {
    assertNotNull(ticker.getPriceChange());
  }

  @Test
  public void testToString() {
    assertNotNull(ticker.toString());
  }
}
