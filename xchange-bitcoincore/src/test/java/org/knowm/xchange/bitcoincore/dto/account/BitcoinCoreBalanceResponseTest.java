package org.knowm.xchange.bitcoincore.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BitcoinCoreBalanceResponseTest {

  @Test
  public void testUnmarshal() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = getClass().getResourceAsStream("/account/example-getbalance.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinCoreBalanceResponse rawBalance = mapper.readValue(is, BitcoinCoreBalanceResponse.class);

    assertThat(rawBalance.getAmount()).isEqualTo(new BigDecimal("68480.47579046"));
  }
}
