package org.knowm.xchange.latoken.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

public class LatokenTradeTest {
  LatokenTrade trade;

  @Before
  public void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/marketdata/latoken-trades-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    LatokenTrades trades = mapper.readValue(is, LatokenTrades.class);
    assertEquals(1, trades.getTrades().size());

    trade = trades.getTrades().get(0);
  }

  @Test
  public void testLatokenTrade() {
    assertNotNull(trade);
  }

  @Test
  public void testGetSide() {
    assertNotNull(trade.getSide());
  }

  @Test
  public void testGetPrice() {
    assertNotNull(trade.getPrice());
  }

  @Test
  public void testGetAmount() {
    assertNotNull(trade.getAmount());
  }

  @Test
  public void testGetTimestamp() {
    assertNotNull(trade.getTimestamp());
  }

  @Test
  public void testToString() {
    assertNotNull(trade.toString());
  }
}
