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
package com.xeiam.xchange.mtgox.v2.dto.trade.polling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.mtgox.v2.dto.MtGoxValue;

/**
 * Data object representing Open Orders from MtGox
 */
public final class MtGoxOrderResultTrade {

  private final MtGoxValue amount;
  private final String currency;
  private final String date;
  private final String item;
  private final MtGoxValue price;
  private final String primary;
  private final String properties;
  private final String tradeId;
  private final String type;

  /**
   * Constructor
   * 
   * @param amount
   * @param currency
   * @param date
   * @param item
   * @param price
   * @param primary
   * @param properties
   * @param tradeId
   * @param type
   */
  public MtGoxOrderResultTrade(@JsonProperty("amount") MtGoxValue amount, @JsonProperty("currency") String currency, @JsonProperty("date") String date, @JsonProperty("item") String item,
      @JsonProperty("price") MtGoxValue price, @JsonProperty("primary") String primary, @JsonProperty("properties") String properties, @JsonProperty("trade_id") String tradeId,
      @JsonProperty("type") String type) {

    this.amount = amount;
    this.currency = currency;
    this.date = date;
    this.item = item;
    this.price = price;
    this.primary = primary;
    this.properties = properties;
    this.tradeId = tradeId;
    this.type = type;
  }

  public MtGoxValue getAmount() {

    return amount;
  }

  public String getCurrency() {

    return currency;
  }

  public String getDate() {

    return date;
  }

  public String getItem() {

    return item;
  }

  public MtGoxValue getPrice() {

    return price;
  }

  public String getPrimary() {

    return primary;
  }

  public String getProperties() {

    return properties;
  }

  public String getTradeId() {

    return tradeId;
  }

  public String getType() {

    return type;
  }

  @Override
  public String toString() {

    return "MtGoxOrderResultTrade [amount=" + amount + ", currency=" + currency + ", date=" + date + ", item=" + item + ", price=" + price + ", primary=" + primary + ", price=" + price
        + ", properties=" + properties + ", tradeId=" + tradeId + ", type=" + type + "]";
  }

}
