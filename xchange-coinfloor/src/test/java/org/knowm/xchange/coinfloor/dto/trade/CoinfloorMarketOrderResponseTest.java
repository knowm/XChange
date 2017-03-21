package org.knowm.xchange.coinfloor.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinfloorMarketOrderResponseTest {
  @Test
  public void unmarshalTest() throws IOException {
    InputStream is = getClass().getResourceAsStream("/trade/example-place-market-order.json");
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorMarketOrderResponse response = mapper.readValue(is, CoinfloorMarketOrderResponse.class);

    assertThat(response.getRemaining()).isEqualTo("0.0000");
  }
}
