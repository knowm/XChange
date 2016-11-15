package org.knowm.xchange.btcchina.dto.trade.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaMarketDepthOrder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCChinaGetMarketDepthResponseTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testBTCChinaGetMarketDepthResponse() throws IOException {

    BTCChinaGetMarketDepthResponse response = mapper.readValue(getClass().getResourceAsStream("getMarketDepth2.json"),
        BTCChinaGetMarketDepthResponse.class);
    assertEquals("1", response.getId());

    BTCChinaMarketDepthOrder[] bids = response.getResult().getMarketDepth().getBids();
    BTCChinaMarketDepthOrder[] asks = response.getResult().getMarketDepth().getAsks();
    assertEquals(2, bids.length);
    assertEquals(2, asks.length);

    assertEquals(new BigDecimal("99"), bids[0].getPrice());
    assertEquals(new BigDecimal("1"), bids[0].getAmount());

    assertEquals(new BigDecimal("98"), bids[1].getPrice());
    assertEquals(new BigDecimal("2"), bids[1].getAmount());

    assertEquals(new BigDecimal("100"), asks[0].getPrice());
    assertEquals(new BigDecimal("0.997"), asks[0].getAmount());

    assertEquals(new BigDecimal("101"), asks[1].getPrice());
    assertEquals(new BigDecimal("2"), asks[1].getAmount());
  }

}
