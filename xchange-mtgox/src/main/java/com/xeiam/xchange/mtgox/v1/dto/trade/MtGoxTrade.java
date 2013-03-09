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
package com.xeiam.xchange.mtgox.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Trade from Mt Gox
 * </p>
 * Auto-generated using the simplest types possible with conversion delegated to the adapter
 */
public final class MtGoxTrade {

  private final double amount;
  private final long amountInt;
  private final Long date;
  private final String item;
  private final double price;
  private final String priceCurrency;
  private final long priceInt;
  private final String primary;
  private final String properties;
  private final long tid;
  private final String tradeType;

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
   * @param primary
   * @param properties
   * @param tid
   * @param tradeType
   */
  public MtGoxTrade(@JsonProperty("amount") double amount, @JsonProperty("amount_int") long amountInt, @JsonProperty("date") Long date, @JsonProperty("item") String item,
      @JsonProperty("price") double price, @JsonProperty("price_currency") String priceCurrency, @JsonProperty("price_int") long priceInt, @JsonProperty("primary") String primary,
      @JsonProperty("properties") String properties, @JsonProperty("tid") long tid, @JsonProperty("trade_type") String tradeType) {

    this.amount = amount;
    this.amountInt = amountInt;
    this.date = date;
    this.item = item;
    this.price = price;
    this.priceCurrency = priceCurrency;
    this.priceInt = priceInt;
    this.primary = primary;
    this.properties = properties;
    this.tid = tid;
    this.tradeType = tradeType;
  }

  public double getAmount() {

    return amount;
  }

  public long getAmountInt() {

    return amountInt;
  }

  public Long getDate() {

    return date;
  }

  public String getItem() {

    return item;
  }

  public double getPrice() {

    return price;
  }

  public String getPriceCurrency() {

    return priceCurrency;
  }

  public long getPriceInt() {

    return priceInt;
  }

  public String getPrimary() {

    return primary;
  }

  public String getProperties() {

    return properties;
  }

  public long getTid() {

    return tid;
  }

  public String getTradeType() {

    return tradeType;
  }

  @Override
  public String toString() {

    return "MtGoxTrade [amount=" + amount + ", amountInt=" + amountInt + ", date=" + date + ", item=" + item + ", price=" + price + ", priceCurrency=" + priceCurrency + ", priceInt=" + priceInt
        + ", primary=" + primary + ", properties=" + properties + ", tid=" + tid + ", tradeType=" + tradeType + "]";
  }

}
