/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.justcoin.service.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.joda.money.BigMoney;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.justcoin.JustcoinUtils;
import com.xeiam.xchange.justcoin.dto.account.JustcoinBalance;

/**
 * @author jamespedwards42
 */
public class JustcoinAccountBalancesTest {

  private String currency;
  private BigMoney balance;
  private JustcoinBalance justcoinBalance;

  @Before
  public void before() {

    // initialize expected values
    currency = Currencies.LTC;
    balance = MoneyUtils.parseMoney(currency, BigDecimal.valueOf(0.00055586));
    justcoinBalance = new JustcoinBalance(currency, balance.getAmount(), BigDecimal.ZERO, BigDecimal.valueOf(0.00055586));
  }

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = JustcoinAccountBalancesTest.class.getResourceAsStream("/account/example-account-info-balances-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final JustcoinBalance[] justcoinBalances = mapper.readValue(is, new JustcoinBalance[0].getClass());

    // Verify that the example data was unmarshalled correctly
    assertThat(justcoinBalances.length).isEqualTo(JustcoinUtils.CURRENCIES.size());
    final JustcoinBalance ltcBalance = justcoinBalances[3];
    assertThat(ltcBalance).isEqualTo(justcoinBalance);
    assertThat(ltcBalance.getHold().scale()).isEqualTo(8);
  }

  @Test
  public void testAdapter() {

    final AccountInfo acctInfo = JustcoinAdapters.adaptAccountInfo("test", new JustcoinBalance[] { justcoinBalance });

    assertThat(acctInfo.getBalance(balance.getCurrencyUnit())).isEqualTo(balance);
    assertThat(acctInfo.getWallets().size()).isEqualTo(1);
    assertThat(acctInfo.getTradingFee()).isNull();
  }
}
