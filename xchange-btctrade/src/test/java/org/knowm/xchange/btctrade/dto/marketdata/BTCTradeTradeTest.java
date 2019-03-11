package org.knowm.xchange.btctrade.dto.marketdata;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;

public class BTCTradeTradeTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testBTCTradeTrade() throws IOException {

    BTCTradeTrade[] trades =
        mapper.readValue(getClass().getResource("trades.json"), BTCTradeTrade[].class);

    assertEquals(1403397140L, trades[0].getDate());
    assertEquals(new BigDecimal("3703"), trades[0].getPrice());
    assertEquals(new BigDecimal("3.50000000000"), trades[0].getAmount());
    assertEquals(2895575L, trades[0].getTid());
    assertEquals("buy", trades[0].getType());

    assertEquals(1403397191L, trades[1].getDate());
    assertEquals(new BigDecimal("3704"), trades[1].getPrice());
    assertEquals(new BigDecimal("0.00200000000"), trades[1].getAmount());
    assertEquals(2895576L, trades[1].getTid());
    assertEquals("buy", trades[1].getType());

    assertEquals(1403428819L, trades[trades.length - 2].getDate());
    assertEquals(new BigDecimal("3740.01"), trades[trades.length - 2].getPrice());
    assertEquals(new BigDecimal("0.01000000000"), trades[trades.length - 2].getAmount());
    assertEquals(2896235L, trades[trades.length - 2].getTid());
    assertEquals("sell", trades[trades.length - 2].getType());

    assertEquals(1403428797L, trades[trades.length - 1].getDate());
    assertEquals(new BigDecimal("3752"), trades[trades.length - 1].getPrice());
    assertEquals(new BigDecimal("16.70000000000"), trades[trades.length - 1].getAmount());
    assertEquals(2896239L, trades[trades.length - 1].getTid());
    assertEquals("buy", trades[trades.length - 1].getType());
  }
}
