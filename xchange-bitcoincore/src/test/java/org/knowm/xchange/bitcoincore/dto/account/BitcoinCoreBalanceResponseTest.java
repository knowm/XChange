package org.knowm.xchange.bitcoincore.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BitcoinCoreBalanceResponseTest {

  @Test
  void unmarshal() throws Exception {
    // Read in the JSON from the example resources
    InputStream is = getClass().getResourceAsStream("/account/example-getbalance.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinCoreBalanceResponse rawBalance = mapper.readValue(is, BitcoinCoreBalanceResponse.class);

    assertThat(rawBalance.getAmount()).isEqualTo(new BigDecimal("68480.47579046"));
  }
}
