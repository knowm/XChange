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
package com.xeiam.xchange.hitbtc.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcBalance {

  private final String currencyCode;
  private final BigDecimal cash;
  private final BigDecimal reserved;

  public HitbtcBalance(@JsonProperty("currency_code") String currencyCode, @JsonProperty("cash") BigDecimal cash, @JsonProperty("reserved") BigDecimal reserved) {

    this.currencyCode = currencyCode;
    this.cash = cash;
    this.reserved = reserved;
  }

  public String getCurrencyCode() {

    return currencyCode;
  }

  public BigDecimal getCash() {

    return cash;
  }

  public BigDecimal getReserved() {

    return reserved;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("HitbtcBalance [currencyCode=");
    builder.append(currencyCode);
    builder.append(", cash=");
    builder.append(cash);
    builder.append(", reserved=");
    builder.append(reserved);
    builder.append("]");
    return builder.toString();
  }
}
