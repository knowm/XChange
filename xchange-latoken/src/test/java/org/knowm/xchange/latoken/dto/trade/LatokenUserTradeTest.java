package org.knowm.xchange.latoken.dto.trade;

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

public class LatokenUserTradeTest {
  LatokenUserTrade trade;

  @Before
  public void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/trade/latoken-user-trades-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    LatokenUserTrades trades = mapper.readValue(is, LatokenUserTrades.class);

    trade = trades.getTrades().get(0);
  }

  @Test
  public void testLatokenUserTrade() {
    assertNotNull(trade);
  }

  @Test
  public void testGetId() {
    assertNotNull(trade.getId());
  }

  @Test
  public void testGetOrderId() {
    assertNotNull(trade.getOrderId());
  }

  @Test
  public void testGetFee() {
    assertNotNull(trade.getFee());
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
  public void testGetTime() {
    assertNotNull(trade.getTime());
  }

  @Test
  public void testToString() {
    assertNotNull(trade.toString());
  }
}
