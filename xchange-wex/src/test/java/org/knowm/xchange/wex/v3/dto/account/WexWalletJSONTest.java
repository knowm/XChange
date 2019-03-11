package org.knowm.xchange.wex.v3.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test WexDepth JSON parsing */
public class WexWalletJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WexWalletJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/wex/v3/account/example-account-info-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    WexAccountInfoReturn ai = mapper.readValue(is, WexAccountInfoReturn.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(ai.getReturnValue().getRights().isInfo()).isTrue();
    assertThat(ai.getReturnValue().getFunds().get("btc")).isEqualTo(new BigDecimal("0.1"));
  }
}
