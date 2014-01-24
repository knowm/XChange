package com.xeiam.xchange.justcoin.service.account;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.justcoin.JustcoinUtils;
import com.xeiam.xchange.justcoin.dto.account.JustcoinBalance;

/**
 * @author jamespedwards42
 */
public class JustcoinBalancesJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = JustcoinBalancesJsonTest.class.getResourceAsStream("/account/example-account-info-balances-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final JustcoinBalance[] justcoinBalances = mapper.readValue(is, new JustcoinBalance[0].getClass());

    // Verify that the example data was unmarshalled correctly
    System.out.println(Arrays.toString(justcoinBalances));
    assertThat(justcoinBalances.length).isEqualTo(JustcoinUtils.CURRENCIES.size());
    final JustcoinBalance ltcBalance = justcoinBalances[3];
    assertTrue(ltcBalance.getBalance().compareTo(BigDecimal.valueOf(0.00055586)) == 0);
    assertTrue(ltcBalance.getHold().compareTo(BigDecimal.valueOf(0)) == 0);
    assertThat(ltcBalance.getHold().scale()).isEqualTo(8);
    assertTrue(ltcBalance.getAvailable().compareTo(BigDecimal.valueOf(0.00055586)) == 0);
  }
}
