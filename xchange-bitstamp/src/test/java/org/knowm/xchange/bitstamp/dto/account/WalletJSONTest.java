package org.knowm.xchange.bitstamp.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.knowm.xchange.bitstamp.dto.account.BitstampBalance.Balance;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test BitStamp Full Depth JSON parsing
 */
public class WalletJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = WalletJSONTest.class.getResourceAsStream("/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampBalance bitstampBalance = mapper.readValue(is, BitstampBalance.class);

    Map<String, Balance> balances = new HashMap<>();
    for (Balance b : bitstampBalance.getBalances()) {
        balances.put(b.getCurrency(), b);
    }
    
    // Verify that the example data was unmarshalled correctly
    assertThat(balances.get("btc").getAvailable()).isEqualTo(new BigDecimal("6.99990000"));
    assertThat(balances.get("btc").getReserved()).isEqualTo(new BigDecimal("0"));
    assertThat(balances.get("btc").getBalance()).isEqualTo(new BigDecimal("6.99990000"));
    assertThat(balances.get("usd").getAvailable()).isEqualTo(new BigDecimal("0.00"));
    assertThat(balances.get("usd").getBalance()).isEqualTo(new BigDecimal("172.87"));
    assertThat(balances.get("usd").getReserved()).isEqualTo(new BigDecimal("172.87"));
    assertThat(bitstampBalance.getFee()).isEqualTo(new BigDecimal("0.5000"));
  }
}
