
package org.knowm.xchange.kucoin.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test Account JSON parsing
 */
public class KucoinAccountUnmarshalTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testBalanceUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KucoinAccountUnmarshalTest.class.getResourceAsStream("/account/example-balance.json");
    KucoinCoinBalance balance = mapper.readValue(is, KucoinCoinBalance.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(balance.getCoinType()).isEqualTo("XRB");
    assertThat(balance.getBalance()).isEqualTo(BigDecimal.valueOf(0.06870691));
    assertThat(balance.getFreezeBalance()).isEqualTo(BigDecimal.valueOf(0d));
  }

}