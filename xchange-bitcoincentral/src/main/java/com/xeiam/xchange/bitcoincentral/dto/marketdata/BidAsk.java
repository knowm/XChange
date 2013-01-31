/**
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitcoincentral.dto.marketdata;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author timmolter
 */
public final class BidAsk {

  private final BigDecimal amount;
  private final String currency;
  private final BigDecimal price;
  private final BigDecimal timestamp;

  /**
   * Constructor
   * 
   * @param amount
   * @param currency
   * @param price
   * @param timestamp
   */
  public BidAsk(@JsonProperty("amount") BigDecimal amount, @JsonProperty("currency") String currency, @JsonProperty("price") BigDecimal price, @JsonProperty("timestamp") BigDecimal timestamp) {

    this.amount = amount;
    this.currency = currency;
    this.price = price;
    this.timestamp = timestamp;
  }

  public BigDecimal getAmount() {

    return this.amount;
  }

  public String getCurrency() {

    return this.currency;
  }

  public BigDecimal getPrice() {

    return this.price;
  }

  public BigDecimal getTimestamp() {

    return this.timestamp;
  }

  @Override
  public String toString() {

    return "BidAsk [amount=" + amount + ", currency=" + currency + ", price=" + price + ", timestamp=" + timestamp + "]";
  }

}
