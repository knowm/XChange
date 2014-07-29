package com.xeiam.xchange.bitfinex.v1.service.trade;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;

@Ignore
public class BitfinexTradeDataJSONTest {

  @Test
  public void testOpenOrders() throws IOException {

    InputStream resourceAsStream = BitfinexTradeDataJSONTest.class.getResourceAsStream("/v1/trade/example-open-orders-data.json");
    BitfinexOrderStatusResponse response = new ObjectMapper().readValue(resourceAsStream, BitfinexOrderStatusResponse.class);

    assertEquals(response.getId(), 4003242);
  }
}
