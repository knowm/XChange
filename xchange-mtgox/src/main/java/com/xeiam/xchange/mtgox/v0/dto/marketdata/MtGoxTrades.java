/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 * Copyright (C) 2012 - 2013 Michael Lagace
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
package com.xeiam.xchange.mtgox.v0.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @deprecated Use V2! This will be removed in 1.8.0+
 */
@Deprecated
public class MtGoxTrades {

  private BigDecimal amount;
  private long amount_int;
  private long date;
  private String item;
  private BigDecimal price;
  private String price_currency;
  private long price_int;
  private long tid;
  private String trade_type;

  /**
   * Constructor
   * 
   * @param amount
   * @param amountInt
   * @param date
   * @param item
   * @param price
   * @param priceCurrency
   * @param priceInt
   * @param tid
   * @param tradeType
   */
  public MtGoxTrades(@JsonProperty("amount") BigDecimal amount, @JsonProperty("amount_int") long amountInt, @JsonProperty("date") long date, @JsonProperty("item") String item,
      @JsonProperty("price") BigDecimal price, @JsonProperty("price_currency") String priceCurrency, @JsonProperty("price_int") long priceInt, @JsonProperty("tid") long tid,
      @JsonProperty("trade_type") String tradeType) {

    this.amount = amount;
    this.amount_int = amountInt;
    this.date = date;
    this.item = item;
    this.price = price;
    this.price_currency = priceCurrency;
    this.price_int = priceInt;
    this.tid = tid;
    this.trade_type = tradeType;
  }

  public BigDecimal getAmount() {

    return this.amount;
  }

  public long getAmount_int() {

    return this.amount_int;
  }

  public long getDate() {

    return this.date;
  }

  public String getItem() {

    return this.item;
  }

  public BigDecimal getPrice() {

    return this.price;
  }

  public String getPrice_currency() {

    return this.price_currency;
  }

  public long getPrice_int() {

    return this.price_int;
  }

  public long getTid() {

    return this.tid;
  }

  public String getTrade_type() {

    return this.trade_type;
  }

  public void setTrade_type(String trade_type) {

    this.trade_type = trade_type;
  }

  @Override
  public String toString() {

    return "MtGoxTrade [amount=" + amount + ", date=" + date + ", item=" + item + ", price=" + price + ", tid=" + tid + "]";
  }
}
