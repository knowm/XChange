package org.knowm.xchange.gatecoin.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.gatecoin.dto.account.Results.GatecoinWithdrawResult;

/** Test BitStamp Full Depth JSON parsing */
public class WithdrawFundsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WithdrawFundsJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gatecoin/dto/account/example-withdraw-fund.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinWithdrawResult gatecoinWithdrawResult =
        mapper.readValue(is, GatecoinWithdrawResult.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(gatecoinWithdrawResult.getResponseStatus().getMessage()).isEqualTo("OK");
  }
}
