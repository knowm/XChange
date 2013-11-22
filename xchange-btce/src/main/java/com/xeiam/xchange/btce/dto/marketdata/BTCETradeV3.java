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
package com.xeiam.xchange.btce.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: okhomenko
 * Since:  11/21/13
 */

/**
 * <p>
 * Data object representing single Trade from BTCE API v.3
 * </p>
 */
public class BTCETradeV3 {

  private final BigDecimal amount;
  private final long date;
  private final BigDecimal price;
  private final long tid;
  private String tradeType;

  /**
   * Constructor
   *
   * @param tradeType
   * @param price
   * @param amount
   * @param tid
   * @param date
   */
  public BTCETradeV3(@JsonProperty("type") String tradeType, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount,
                     @JsonProperty("tid") long tid, @JsonProperty("timestamp") long date) {

    this.tradeType = tradeType;
    this.price = price;
    this.amount = amount;
    this.tid = tid;
    this.date = date;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getDate() {

    return date;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public long getTid() {

    return tid;
  }

  public String getTradeType() {

    return tradeType;
  }

  @Override
  public String toString() {

    return "BTCETradeV3 [amount=" + amount + ", timestamp=" + date + ", price=" + price + ", tid=" + tid + ", type=" + tradeType + "]";
  }

}
