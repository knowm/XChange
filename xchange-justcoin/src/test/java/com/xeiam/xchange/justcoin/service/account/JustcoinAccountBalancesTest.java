package com.xeiam.xchange.justcoin.service.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.justcoin.dto.account.JustcoinBalance;

/**
 * @author jamespedwards42
 */
public class JustcoinAccountBalancesTest {

  private String currency;
  private BigDecimal balance;
  private JustcoinBalance justcoinBalance;

  @Before
  public void before() {

    // initialize expected values
    currency = Currencies.LTC;
    balance = BigDecimal.valueOf(0.00055586);
    justcoinBalance = new JustcoinBalance(currency, balance, BigDecimal.ZERO, BigDecimal.valueOf(0.00055586));
  }

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = JustcoinAccountBalancesTest.class.getResourceAsStream("/account/example-account-info-balances-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final JustcoinBalance[] justcoinBalances = mapper.readValue(is, new JustcoinBalance[0].getClass());

    // Verify that the example data was unmarshalled correctly
    final JustcoinBalance ltcBalance = justcoinBalances[3];
    assertThat(ltcBalance).isEqualTo(justcoinBalance);
    assertThat(ltcBalance.getHold().scale()).isEqualTo(8);
  }

  @Test
  public void testAdapter() {

    final AccountInfo acctInfo = JustcoinAdapters.adaptAccountInfo("test", new JustcoinBalance[] { justcoinBalance });

    assertThat(acctInfo.getWallets().size()).isEqualTo(1);
    assertThat(acctInfo.getTradingFee()).isNull();
  }
}
