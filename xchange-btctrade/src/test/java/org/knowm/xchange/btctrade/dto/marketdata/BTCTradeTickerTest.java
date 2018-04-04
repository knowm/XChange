package org.knowm.xchange.btctrade.dto.marketdata;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;

public class BTCTradeTickerTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testBTCTradeTicker() throws IOException {

    BTCTradeTicker ticker =
        mapper.readValue(getClass().getResource("ticker.json"), BTCTradeTicker.class);
    assertEquals(new BigDecimal("3760"), ticker.getHigh());
    assertEquals(new BigDecimal("3658"), ticker.getLow());
    assertEquals(new BigDecimal("3752"), ticker.getBuy());
    assertEquals(new BigDecimal("3758"), ticker.getSell());
    assertEquals(new BigDecimal("3757"), ticker.getLast());
    assertEquals(new BigDecimal("11009.76"), ticker.getVol());
  }
}
