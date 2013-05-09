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
package com.xeiam.xchange.mtgox.v2.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing a buy or sell order from Mt Gox
 */
public final class MtGoxOrder {

  private final BigDecimal price;
  private final BigDecimal amount;
  private final long priceInt;
  private final long amountInt;
  private final long stamp;

  /**
   * Constructor
   * 
   * @param price
   * @param amount
   * @param priceInt
   * @param amountInt
   * @param stamp
   */
  public MtGoxOrder(@JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount, @JsonProperty("price_int") long priceInt, @JsonProperty("amount_int") long amountInt,
      @JsonProperty("stamp") long stamp) {

    this.price = price;
    this.amount = amount;
    this.priceInt = priceInt;
    this.amountInt = amountInt;
    this.stamp = stamp;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getPriceInt() {

    return priceInt;
  }

  public long getAmountInt() {

    return amountInt;
  }

  public long getStamp() {

    return stamp;
  }

  @Override
  public String toString() {

    return "MtGoxOrder [price=" + price + ", amount=" + amount + ", priceInt=" + priceInt + ", amountInt=" + amountInt + ", stamp=" + stamp + "]";
  }

}
