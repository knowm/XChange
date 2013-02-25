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
package com.xeiam.xchange.dto.trade;

import java.math.BigDecimal;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

/**
 * <p>
 * DTO representing a Wallet
 * </p>
 * <p>
 * This is simply defined by an amount of money in a given currency, contained in the cash object.
 * </p>
 * <p>
 * This class is immutable.
 * </p>
 */
public final class Wallet {

  // TODO BigMoney contains a currency representation is this required?
  private final String currency;

  private final BigMoney balance;

  /**
   * Constructor
   * 
   * @param currency The underlying currency
   * @param balance The balance
   */
  public Wallet(String currency, BigMoney balance) {

    this.currency = currency;
    this.balance = balance;
  }

  public static Wallet createInstance(String currency, BigDecimal amount) {

    return new Wallet(currency, BigMoney.of(CurrencyUnit.of(currency), amount));
    // return new Wallet(currency, MoneyUtils.parseMoney(currency, amount));
  }

  public String getCurrency() {

    return currency;
  }

  public BigMoney getBalance() {

    return balance;
  }

  @Override
  public String toString() {

    return "Wallet [currency=" + currency + ", balance=" + balance + "]";
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((balance == null) ? 0 : balance.hashCode());
    result = prime * result + ((currency == null) ? 0 : currency.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Wallet other = (Wallet) obj;
    if (balance == null) {
      if (other.balance != null) {
        return false;
      }
    } else if (!balance.equals(other.balance)) {
      return false;
    }
    if (currency == null) {
      if (other.currency != null) {
        return false;
      }
    } else if (!currency.equals(other.currency)) {
      return false;
    }
    return true;
  }

}
