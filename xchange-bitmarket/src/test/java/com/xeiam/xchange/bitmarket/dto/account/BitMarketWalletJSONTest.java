package com.xeiam.xchange.bitmarket.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author
 */
public class BitMarketWalletJSONTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitMarketWalletJSONTest.class.getResourceAsStream("/account/example-info-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitMarketAccountInfoResponse response = mapper.readValue(is, BitMarketAccountInfoResponse.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getData().getBalance().getAvailable().get("PLN")).isEqualTo(new BigDecimal("4.166000000000"));
    assertThat(response.getData().getBalance().getBlocked().size()).isEqualTo(3);
  }
}
