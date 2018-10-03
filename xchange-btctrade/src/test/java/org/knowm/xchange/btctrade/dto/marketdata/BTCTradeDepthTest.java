package org.knowm.xchange.btctrade.dto.marketdata;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;

public class BTCTradeDepthTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testBTCTradeDepth() throws IOException {

    BTCTradeDepth depth =
        mapper.readValue(getClass().getResource("depth.json"), BTCTradeDepth.class);

    assertEquals(50, depth.getAsks().length);
    assertEquals(50, depth.getBids().length);

    assertEquals(new BigDecimal("4045.00000"), depth.getAsks()[0][0]);
    assertEquals(new BigDecimal("1.402"), depth.getAsks()[0][1]);

    assertEquals(new BigDecimal("4044.50000"), depth.getAsks()[1][0]);
    assertEquals(new BigDecimal("0.011"), depth.getAsks()[1][1]);

    assertEquals(new BigDecimal("3756.00000"), depth.getAsks()[49][0]);
    assertEquals(new BigDecimal("0.685"), depth.getAsks()[49][1]);

    assertEquals(new BigDecimal("3730.00000"), depth.getBids()[0][0]);
    assertEquals(new BigDecimal("1.066"), depth.getBids()[0][1]);

    assertEquals(new BigDecimal("3728.01000"), depth.getBids()[1][0]);
    assertEquals(new BigDecimal("3.000"), depth.getBids()[1][1]);

    assertEquals(new BigDecimal("2951.00000"), depth.getBids()[49][0]);
    assertEquals(new BigDecimal("4.000"), depth.getBids()[49][1]);
  }
}
