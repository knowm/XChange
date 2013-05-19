/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.dto.account;

import com.xeiam.xchange.dto.trade.Wallet;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import java.util.List;

/**
 * <p>
 * DTO representing account information
 * </p>
 * <p>
 * Account information is associated with the current balances in various currencies held on the exchange.
 * </p>
 */
public final class AccountInfo {

  private final String username;
  private final List<Wallet> wallets;

  /**
   * @param username The user name
   * @param wallets The available wallets
   */
  public AccountInfo(String username, List<Wallet> wallets) {

    this.username = username;
    this.wallets = wallets;
  }

  /**
   * @return The user name
   */
  public String getUsername() {

    return username;
  }

  /**
   * @return The available wallets (balance and currency)
   */
  public List<Wallet> getWallets() {

    return wallets;
  }

  /**
   * Utility method to locate an exchange balance in the given currency
   * 
   * @param currencyUnit A valid currency unit (e.g. CurrencyUnit.USD or CurrencyUnit.of("BTC"))
   * @return The balance, or zero if not found
   */
  public BigMoney getBalance(CurrencyUnit currencyUnit) {

    for (Wallet wallet : wallets) {
      if (wallet.getBalance().getCurrencyUnit().equals(currencyUnit)) {
        return wallet.getBalance();
      }
    }

    // Not found so treat as zero
    return BigMoney.zero(currencyUnit);
  }

  @Override
  public String toString() {

    return "AccountInfo [username=" + username + ", wallets=" + wallets + "]";
  }

}
