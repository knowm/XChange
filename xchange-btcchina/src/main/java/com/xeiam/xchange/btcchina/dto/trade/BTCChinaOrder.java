/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.btcchina.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaOrder {

  private final long id;
  private final String type;
  private final BigDecimal price;
  private final String currency;
  private final BigDecimal amount;
  private final BigDecimal amountOriginal;
  private final long date;
  private final String status;

  /**
   * Constructor
   * 
   * @param id
   * @param type
   * @param price
   * @param currency
   * @param amount
   * @param amountOriginal
   * @param date
   * @param status
   */
  public BTCChinaOrder(@JsonProperty("id") long id, @JsonProperty("type") String type, @JsonProperty("price") BigDecimal price, @JsonProperty("currency") String currency,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("amount_original") BigDecimal amountOriginal, @JsonProperty("date") long date, @JsonProperty("status") String status) {

    this.id = id;
    this.type = type;
    this.price = price;
    this.currency = currency;
    this.amount = amount;
    this.amountOriginal = amountOriginal;
    this.date = date;
    this.status = status;
  }

  public long getId() {

    return id;
  }

  public String getType() {

    return type;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getAmountOriginal() {

    return amountOriginal;
  }

  public long getDate() {

    return date;
  }

  public String getStatus() {

    return status;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaOrder{id=%d, type=%s, price=%s, currency=%s, amount=%s, amountOriginal=%s, date=%d, status=%s}", id, type, price, currency, amount, amountOriginal, date, status);
  }

}
