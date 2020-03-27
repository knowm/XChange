package org.knowm.xchange.latoken.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

public class LatokenTradesTest {
  LatokenTrades trades;

  @Before
  public void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/marketdata/latoken-trades-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    trades = mapper.readValue(is, LatokenTrades.class);
  }

  @Test
  public void testLatokenTrades() {
    assertNotNull(trades);
  }

  @Test
  public void testGetPairId() {
    assertNotNull(trades.getPairId());
  }

  @Test
  public void testGetSymbol() {
    assertNotNull(trades.getSymbol());
  }

  @Test
  public void testGetTradeCount() {
    assertNotNull(trades.getTradeCount());
  }

  @Test
  public void testGetTrades() {
    assertNotNull(trades.getTrades());
    assertEquals(1, trades.getTrades().size());
  }

  @Test
  public void testToString() {
    assertNotNull(trades.toString());
  }
}
