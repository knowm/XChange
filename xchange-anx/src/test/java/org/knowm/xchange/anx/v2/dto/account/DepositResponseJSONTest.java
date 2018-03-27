package org.knowm.xchange.anx.v2.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** Test ANXDepositResponse JSON parsing */
public class DepositResponseJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        DepositResponseJSONTest.class.getResourceAsStream(
            "/v2/account/example-deposit-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    ANXBitcoinDepositAddress anxBitcoinDepositAddress =
        mapper.readValue(is, ANXBitcoinDepositAddress.class);

    System.out.println(anxBitcoinDepositAddress.toString());

    // Verify that the example data was unmarshalled correctly
    assertThat(anxBitcoinDepositAddress.getAddress())
        .isEqualTo("1GAUBau3nKQYJ1uvMWUfWCdEbMTJ1BXFjW");
  }
}
