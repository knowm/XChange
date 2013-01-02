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
package com.xeiam.xchange.btce.dto.marketdata;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <p>
 * Data object representing a Trade from BTCE
 * </p>
 * 
 * @immutable
 */
public class BTCETrade {

  private final double amount;
  private final long date;
  private final double price;
  private final long tid;
  private String item;
  private String price_currency;
  private String trade_type;

  /**
   * Constructor
   * 
   * @param amount
   * @param date
   * @param price
   * @param tid
   * @param item
   * @param price_currency
   * @param trade_type
   */
  public BTCETrade(@JsonProperty("amount") double amount, @JsonProperty("date") long date, @JsonProperty("price") double price, @JsonProperty("tid") long tid, @JsonProperty("item") String item,
      @JsonProperty("price_currency") String price_currency, @JsonProperty("trade_type") String trade_type) {

    this.amount = amount;
    this.date = date;
    this.price = price;
    this.tid = tid;
    this.item = item;
    this.price_currency = price_currency;
    this.trade_type = trade_type;
  }

  public double getAmount() {

    return amount;
  }

  public long getDate() {

    return date;
  }

  public String getItem() {

    return item;
  }

  public double getPrice() {

    return price;
  }

  public String getPrice_currency() {

    return price_currency;
  }

  public long getTid() {

    return tid;
  }

  public String getTrade_type() {

    return trade_type;
  }

  @Override
  public String toString() {

    return "BTCETrades [amount=" + amount + ", date=" + date + ", price=" + price + ", tid=" + tid + "]";
  }
}
