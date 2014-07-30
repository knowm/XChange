package com.xeiam.xchange.bitstamp.service.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;

/**
 * Test BitStamp Full Depth JSON parsing
 */
public class AccountInfoJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = AccountInfoJSONTest.class.getResourceAsStream("/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampBalance bitstampBalance = mapper.readValue(is, BitstampBalance.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitstampBalance.getBtcAvailable()).isEqualTo(new BigDecimal("6.99990000"));
    assertThat(bitstampBalance.getBtcReserved()).isEqualTo(new BigDecimal("0"));
    assertThat(bitstampBalance.getBtcBalance()).isEqualTo(new BigDecimal("6.99990000"));
    assertThat(bitstampBalance.getUsdAvailable()).isEqualTo(new BigDecimal("0.00"));
    assertThat(bitstampBalance.getUsdBalance()).isEqualTo(new BigDecimal("172.87"));
    assertThat(bitstampBalance.getUsdReserved()).isEqualTo(new BigDecimal("172.87"));
    assertThat(bitstampBalance.getFee()).isEqualTo(new BigDecimal("0.5000"));
  }
}
