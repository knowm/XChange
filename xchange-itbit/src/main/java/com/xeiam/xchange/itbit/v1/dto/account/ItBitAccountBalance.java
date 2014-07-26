/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.itbit.v1.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItBitAccountBalance {

  private final BigDecimal availableBalance;
  private final BigDecimal totalBalance;
  private final String currency;

  public ItBitAccountBalance(@JsonProperty("availableBalance") BigDecimal availableBalance, @JsonProperty("totalBalance") BigDecimal totalBalance, @JsonProperty("currency") String currency) {

    this.availableBalance = availableBalance;
    this.totalBalance = totalBalance;
    this.currency = currency;
  }

  public BigDecimal getAvailableBalance() {

    return availableBalance;
  }

  public BigDecimal getTotalBalance() {

    return totalBalance;
  }

  public String getCurrency() {

    return currency;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("ItBitAccountBalance [availableBalance=");
    builder.append(availableBalance);
    builder.append(", totalBalance=");
    builder.append(totalBalance);
    builder.append(", currency=");
    builder.append(currency);
    builder.append("]");
    return builder.toString();
  }

}
