package org.knowm.xchange.btce.v3.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test BTCEDepth JSON parsing
 */
public class BTCEWalletJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCEWalletJSONTest.class.getResourceAsStream("/v3/account/example-account-info-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCEAccountInfoReturn ai = mapper.readValue(is, BTCEAccountInfoReturn.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(ai.getReturnValue().getRights().isInfo()).isTrue();
    assertThat(ai.getReturnValue().getFunds().get("btc")).isEqualTo(new BigDecimal("0.1"));
  }
}
