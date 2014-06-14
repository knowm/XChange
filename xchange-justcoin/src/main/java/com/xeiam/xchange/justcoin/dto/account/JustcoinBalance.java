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
package com.xeiam.xchange.justcoin.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class JustcoinBalance {

  private final String currency;
  private final BigDecimal balance;
  private final BigDecimal hold;
  private final BigDecimal available;

  /**
   * Constructor
   * 
   * @param currency
   * @param balance
   * @param hold
   * @param available
   */
  public JustcoinBalance(final @JsonProperty("currency") String currency, final @JsonProperty("balance") BigDecimal balance, final @JsonProperty("hold") BigDecimal hold,
      final @JsonProperty("available") BigDecimal available) {

    this.currency = currency;
    this.balance = balance;
    this.hold = hold;
    this.available = available;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getBalance() {

    return balance;
  }

  public BigDecimal getHold() {

    return hold;
  }

  public BigDecimal getAvailable() {

    return available;
  }

  @Override
  public String toString() {

    return "JustcoinBalance [currency=" + currency + ", balance=" + balance + ", hold=" + hold + ", available=" + available + "]";
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
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
    JustcoinBalance other = (JustcoinBalance) obj;
    if (available == null) {
      if (other.available != null) {
        return false;
      }
    }
    else if (available.compareTo(other.available) != 0) {
      return false;
    }
    if (balance == null) {
      if (other.balance != null) {
        return false;
      }
    }
    else if (balance.compareTo(other.balance) != 0) {
      return false;
    }
    if (currency == null) {
      if (other.currency != null) {
        return false;
      }
    }
    else if (!currency.equals(other.currency)) {
      return false;
    }
    if (hold == null) {
      if (other.hold != null) {
        return false;
      }
    }
    else if (hold.compareTo(other.hold) != 0) {
      return false;
    }
    return true;
  }
}
