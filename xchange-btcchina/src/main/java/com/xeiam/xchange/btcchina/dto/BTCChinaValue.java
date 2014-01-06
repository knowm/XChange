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
package com.xeiam.xchange.btcchina.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaValue {

  private final String currency;
  private final String symbol;
  private final BigDecimal amount;
  private final String amountInteger;
  private final BigDecimal amountDecimal;

  public BTCChinaValue(@JsonProperty("currency") String currency, @JsonProperty("symbol") String symbol, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("amount_integer") String amountInteger, @JsonProperty("amount_decimal") BigDecimal amountDecimal) {

    this.currency = currency;
    this.symbol = symbol;
    this.amount = amount;
    this.amountInteger = amountInteger;
    this.amountDecimal = amountDecimal;
  }

  public String getCurrency() {

    return currency;
  }

  public String getSymbol() {

    return symbol;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getAmountInteger() {

    return amountInteger;
  }

  public BigDecimal getAmountDecimal() {

    return amountDecimal;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaValue{currency=%s, symbol=%s, amount=%s, amountInteger=%s, amountDecimal=%s}", currency, symbol, amount, amountInteger, amountDecimal);
  }

}
