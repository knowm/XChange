package com.xeiam.xchange.justcoin.service.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.justcoin.dto.account.JustcoinDepositAddress;

/**
 * @author jamespedwards42
 */
public class JustcoinDepositAddressTest {

  final String address = "1Fpx2Q6J8TX3PZffgEBTpWSHG37FQBgqKB";

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = JustcoinDepositAddressTest.class.getResourceAsStream("/account/example-deposit-address-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final JustcoinDepositAddress justcoinDepositAddress = mapper.readValue(is, JustcoinDepositAddress.class);

    assertThat(justcoinDepositAddress.getAddress()).isEqualTo(address);
  }

}
