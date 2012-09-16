/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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

import org.joda.money.BigMoney;

/**
 * <p>
 * Data object representing a Wallet, which is simply defined by an amount of money in a given currency, contained in the cash object, This class is immutable.
 * </p>
 */
public class Wallet {

  private final BigMoney balance;

  /**
   * Constructor
   * 
   * @param balance The amount
   */
  public Wallet(BigMoney balance) {

    super();
    this.balance = balance;
  }

  public BigMoney getBalance() {

    return balance;
  }

  @Override
  public String toString() {

    return "Wallet [balance= " + balance + "]";
  }

  @Override
  public int hashCode() {

    return balance.hashCode();
  }

  // two wallets are the same of their balances are equal
  // Note: Does not take currency into account
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
    return true;
  }

}
