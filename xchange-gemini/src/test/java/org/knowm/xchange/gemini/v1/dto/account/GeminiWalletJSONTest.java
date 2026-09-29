package org.knowm.xchange.gemini.v1.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/** Test BTCEDepth JSON parsing */
public class GeminiWalletJSONTest {

  @Test
  void unmarshal() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        GeminiWalletJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/account/example-account-info-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GeminiBalancesResponse readValue = mapper.readValue(is, GeminiBalancesResponse.class);

    assertThat(new BigDecimal("8.53524686").toString()).isEqualTo(readValue.getAmount().toString());
  }
}
