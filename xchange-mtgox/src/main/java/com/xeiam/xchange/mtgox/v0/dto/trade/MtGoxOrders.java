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
package com.xeiam.xchange.mtgox.v0.dto.trade;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author timmolter
 */
public final class MtGoxOrders {

  private final BigDecimal amount;
  private final long amountInt;
  private final String currency;
  private final Number dark;
  private final long date;
  private final String item;
  private final String oid;
  private final BigDecimal price;
  private final long priceInt;
  private final String priority;
  private final String realStatus;
  private final Number status;
  private final Number type;

  /**
   * Constructor
   * 
   * @param amount
   * @param amount_int
   * @param currency
   * @param dark
   * @param date
   * @param item
   * @param oid
   * @param price
   * @param price_int
   * @param priority
   * @param real_status
   * @param status
   * @param type
   */
  public MtGoxOrders(@JsonProperty("amount") BigDecimal amount, @JsonProperty("amount_int") long amount_int, @JsonProperty("currency") String currency, @JsonProperty("dark") Number dark,
      @JsonProperty("date") long date, @JsonProperty("item") String item, @JsonProperty("oid") String oid, @JsonProperty("price") BigDecimal price, @JsonProperty("price_int") long price_int,
      @JsonProperty("priority") String priority, @JsonProperty("real_status") String real_status, @JsonProperty("status") Number status, @JsonProperty("type") Number type) {

    this.amount = amount;
    this.amountInt = amount_int;
    this.currency = currency;
    this.dark = dark;
    this.date = date;
    this.item = item;
    this.oid = oid;
    this.price = price;
    this.priceInt = price_int;
    this.priority = priority;
    this.realStatus = real_status;
    this.status = status;
    this.type = type;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getAmountInt() {

    return amountInt;
  }

  public String getCurrency() {

    return currency;
  }

  public Number getDark() {

    return dark;
  }

  public long getDate() {

    return date;
  }

  public String getItem() {

    return item;
  }

  public String getOid() {

    return oid;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public long getPriceInt() {

    return priceInt;
  }

  public String getPriority() {

    return priority;
  }

  public String getRealStatus() {

    return realStatus;
  }

  public Number getStatus() {

    return status;
  }

  public Number getType() {

    return type;
  }

  @Override
  public String toString() {

    return "Orders [amount=" + amount + ", amountInt=" + amountInt + ", currency=" + currency + ", dark=" + dark + ", date=" + date + ", item=" + item + ", oid=" + oid + ", price=" + price
        + ", priceInt=" + priceInt + ", priority=" + priority + ", realStatus=" + realStatus + ", status=" + status + ", type=" + type + "]";
  }

}
