package org.knowm.xchange.gatecoin.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.knowm.xchange.gatecoin.dto.account.Results.GatecoinDepositAddressResult;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author sumedha
 */
public class DepositAddressJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = WithdrawFundsJSONTest.class.getResourceAsStream("/account/example-deposit-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinDepositAddressResult gatecoinDepositAddressResult = mapper.readValue(is, GatecoinDepositAddressResult.class);
    GatecoinDepositAddress[] gatecoinDepositAddress = gatecoinDepositAddressResult.getAddresses();

    // Verify that the example data was unmarshalled correctly

    assertThat(gatecoinDepositAddressResult.getResponseStatus().getMessage()).isEqualTo("OK");

    assertThat(gatecoinDepositAddress[0].getCurrency()).isEqualTo("BTC");
    assertThat(gatecoinDepositAddress[0].getAddress()).isEqualTo("n3v78gsrYPd1Qt8GYgfbFNFruBeuauudEj");

  }
}
