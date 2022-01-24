package org.knowm.xchange.latoken.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

public class LatokenOrderbookTest {
  LatokenOrderbook orderbook;

  @Before
  public void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/marketdata/latoken-orderbook-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    orderbook = mapper.readValue(is, LatokenOrderbook.class);
  }

  @Test
  public void testLatokenOrderbook() {
    assertNotNull(orderbook);
  }

  @Test
  public void testGetPairId() {
    assertNotNull(orderbook.getPairId());
  }

  @Test
  public void testGetSymbol() {
    assertNotNull(orderbook.getSymbol());
  }

  @Test
  public void testGetSpread() {
    assertNotNull(orderbook.getSpread());
  }

  @Test
  public void testGetAsks() {
    assertNotNull(orderbook.getAsks());
    assertEquals(1, orderbook.getAsks().size());

    PriceLevel level = orderbook.getAsks().get(0);
    assertNotNull(level.getPrice());
    assertNotNull(level.getAmount());
  }

  @Test
  public void testGetBids() {
    assertNotNull(orderbook.getBids());
    assertEquals(1, orderbook.getBids().size());

    PriceLevel level = orderbook.getAsks().get(0);
    assertNotNull(level.getPrice());
    assertNotNull(level.getAmount());
  }

  @Test
  public void testToString() {
    assertNotNull(orderbook.toString());
  }
}
