package org.knowm.xchange.cryptonit2.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/** Test Cryptonit Full Depth JSON parsing */
public class WalletJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WalletJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptonit2/dto/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitBalance cryptonitBalance = mapper.readValue(is, CryptonitBalance.class);

    Map<String, CryptonitBalance.Balance> balances = new HashMap<>();
    for (CryptonitBalance.Balance b : cryptonitBalance.getBalances()) {
      balances.put(b.getCurrency(), b);
    }

    // Verify that the example data was unmarshalled correctly
    assertThat(balances.get("btc").getAvailable()).isEqualTo(new BigDecimal("6.99990000"));
    assertThat(balances.get("btc").getReserved()).isEqualTo(new BigDecimal("0"));
    assertThat(balances.get("btc").getBalance()).isEqualTo(new BigDecimal("6.99990000"));
    assertThat(balances.get("usd").getAvailable()).isEqualTo(new BigDecimal("0.00"));
    assertThat(balances.get("usd").getBalance()).isEqualTo(new BigDecimal("172.87"));
    assertThat(balances.get("usd").getReserved()).isEqualTo(new BigDecimal("172.87"));
    assertThat(cryptonitBalance.getFee()).isEqualTo(new BigDecimal("0.5000"));
  }
}
