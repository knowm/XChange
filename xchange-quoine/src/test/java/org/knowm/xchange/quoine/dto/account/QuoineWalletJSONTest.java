package org.knowm.xchange.quoine.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test QuoineTicker JSON parsing */
public class QuoineWalletJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        QuoineWalletJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/quoine/dto/account/example-account-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    QuoineAccountInfo quoineAccountInfo = mapper.readValue(is, QuoineAccountInfo.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(quoineAccountInfo.getBitcoinAccount().getId()).isEqualTo(59);
    assertThat(quoineAccountInfo.getBitcoinAccount().getBalance())
        .isEqualTo(new BigDecimal("2.63499784"));
    assertThat(quoineAccountInfo.getFiatAccounts()[0].getId()).isEqualTo(52);
  }
}
