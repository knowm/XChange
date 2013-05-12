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
 * Data object representing Open Orders from Mt Gox
 */
public final class MtGoxOpenOrder {

  private final String oid;
  private final String currency;
  private final String item;
  private final String type;
  private final MtGoxValue amount;
  private final MtGoxValue invalidAmount;
  private final MtGoxValue price;
  private final String status;
  private final long date;
  private final long priority;

  /**
   * Constructor
   * 
   * @param oid
   * @param currency
   * @param item
   * @param type
   * @param amount
   * @param invalidAmount
   * @param price
   * @param status
   * @param date
   * @param priority
   */
  public MtGoxOpenOrder(@JsonProperty("oid") String oid, @JsonProperty("currency") String currency, @JsonProperty("item") String item, @JsonProperty("type") String type,
      @JsonProperty("amount") MtGoxValue amount, @JsonProperty("invalid_amount") MtGoxValue invalidAmount, @JsonProperty("price") MtGoxValue price, @JsonProperty("status") String status,
      @JsonProperty("date") long date, @JsonProperty("priority") long priority) {

    this.oid = oid;
    this.currency = currency;
    this.item = item;
    this.type = type;
    this.amount = amount;
    this.invalidAmount = invalidAmount;
    this.price = price;
    this.status = status;
    this.date = date;
    this.priority = priority;
  }

  public String getOid() {

    return oid;
  }

  public String getCurrency() {

    return currency;
  }

  public String getItem() {

    return item;
  }

  public String getType() {

    return type;
  }

  public MtGoxValue getAmount() {

    return amount;
  }

  public MtGoxValue getInvalidAmount() {

    return invalidAmount;
  }

  public MtGoxValue getPrice() {

    return price;
  }

  public String getStatus() {

    return status;
  }

  public long getDate() {

    return date;
  }

  public long getPriority() {

    return priority;
  }

  @Override
  public String toString() {

    return "MtGoxOpenOrder [oid=" + oid + ", currency=" + currency + ", item=" + item + ", type=" + type + ", amount=" + amount + ", invalidAmount=" + invalidAmount + ", price=" + price + ", status="
        + status + ", date=" + date + ", priority=" + priority + "]";
  }

}
