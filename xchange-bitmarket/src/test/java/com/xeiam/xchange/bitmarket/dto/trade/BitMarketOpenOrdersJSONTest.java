package com.xeiam.xchange.bitmarket.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author kfonal
 */
public class BitMarketOpenOrdersJSONTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitMarketOpenOrdersJSONTest.class.getResourceAsStream("/trade/example-orders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitMarketOrdersResponse response = mapper.readValue(is, BitMarketOrdersResponse.class);

    // Verify that the example data was unmarshalled correctly
    BitMarketOrder buyOrderBTCPLN = response.getData().get("BTCPLN").get("buy").get(0);
    BitMarketOrder sellOrderBTCPLN = response.getData().get("BTCPLN").get("sell").get(0);

    assertThat(response.getSuccess()).isTrue();
    assertThat(buyOrderBTCPLN.getAmount()).isEqualTo(new BigDecimal("0.20000000"));
    assertThat(buyOrderBTCPLN.getRate()).isEqualTo(new BigDecimal("3000.0000"));
    assertThat(buyOrderBTCPLN.getId()).isEqualTo(31393);
    assertThat(sellOrderBTCPLN.getAmount()).isEqualTo(new BigDecimal("0.08000000"));
    assertThat(sellOrderBTCPLN.getRate()).isEqualTo(new BigDecimal("4140.0000"));
    assertThat(sellOrderBTCPLN.getId()).isEqualTo(31391);
  }
}
