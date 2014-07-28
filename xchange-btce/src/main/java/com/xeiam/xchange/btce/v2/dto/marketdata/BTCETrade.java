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
package com.xeiam.xchange.btce.v2.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Trade from BTCE
 * </p>
 */
@Deprecated
public class BTCETrade {

  private final BigDecimal amount;
  private final long date;
  private final BigDecimal price;
  private final long tid;
  private String tradeableIdentifier;
  private String currency;
  private String tradeType;

  /**
   * Constructor
   * 
   * @param amount
   * @param date
   * @param price
   * @param tid
   * @param tradeableIdentifier
   * @param currency
   * @param tradeType
   */
  public BTCETrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("date") long date, @JsonProperty("price") BigDecimal price, @JsonProperty("tid") long tid,
      @JsonProperty("item") String tradeableIdentifier, @JsonProperty("price_currency") String currency, @JsonProperty("trade_type") String tradeType) {

    this.amount = amount;
    this.date = date;
    this.price = price;
    this.tid = tid;
    this.tradeableIdentifier = tradeableIdentifier;
    this.currency = currency;
    this.tradeType = tradeType;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getDate() {

    return date;
  }

  public String getTradeableIdentifier() {

    return tradeableIdentifier;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public String getCurrency() {

    return currency;
  }

  public long getTid() {

    return tid;
  }

  public String getTradeType() {

    return tradeType;
  }

  @Override
  public String toString() {

    return "BTCETrade [amount=" + amount + ", date=" + date + ", price=" + price + ", tid=" + tid + ", tradeableIdentifier=" + tradeableIdentifier + ", currency=" + currency + ", tradeType="
        + tradeType + "]";
  }

}
