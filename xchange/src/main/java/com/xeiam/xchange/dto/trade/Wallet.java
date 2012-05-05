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

import net.jcip.annotations.Immutable;

import org.joda.money.BigMoney;

/**
 * <p>
 * Data object representing a Wallet, which is simply defined by an amount of money in a given currency, contained in the cash object, This class is immutable.
 * </p>
 */
@Immutable
public class Wallet {

  private final BigMoney cash;

  /**
   * Constructor
   * 
   * @param cash
   */
  public Wallet(BigMoney cash) {
    super();
    this.cash = cash;
  }

  public BigMoney getCash() {
    return cash;
  }

  @Override
  public String toString() {
    return "Wallet [cash=" + cash + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cash == null) ? 0 : cash.hashCode());
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
    if (cash == null) {
      if (other.cash != null) {
        return false;
      }
    } else if (!cash.equals(other.cash)) {
      return false;
    }
    return true;
  }

}
