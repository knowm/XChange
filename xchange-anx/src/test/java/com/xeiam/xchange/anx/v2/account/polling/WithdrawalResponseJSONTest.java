package com.xeiam.xchange.anx.v2.account.polling;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Ignore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWithdrawalResponse;

/**
 * Test ANXWithdrawalResponse JSON parsing
 */
public class WithdrawalResponseJSONTest {

  @Ignore
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = WithdrawalResponseJSONTest.class.getResourceAsStream("/v2/account/example-withdrawal-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    ANXWithdrawalResponse anxWithdrawalResponse = mapper.readValue(is, ANXWithdrawalResponse.class);

    System.out.println(anxWithdrawalResponse.toString());

    // Verify that the example data was unmarshalled correctly
    // assertThat(anxWithdrawalResponse.getTransactionId()).isEqualTo("9921d2c5abecfd3604e921888b32e48256c914156cc76c4c8eca1ad2709b48e6");
  }
}