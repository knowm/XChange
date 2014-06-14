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
package com.xeiam.xchange.virtex.v2.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Trade from VirtEx
 * </p>
 */
public final class VirtExTrade {

  private final BigDecimal amount;
  private final double date;
  private final BigDecimal price;
  private final long tid;
  private final BigDecimal rate;
  private final String trade_currency;
  private final BigDecimal trade_currency_amount;
  private final String for_currency;
  private final BigDecimal for_currency_amount;

  /**
   * @param amount
   * @param date
   * @param price
   * @param tid
   * @param rate
   * @param trade_currency
   * @param trade_currency_amount
   * @param for_currency
   * @param for_currency_amount
   */
  public VirtExTrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("date") double date, @JsonProperty("price") BigDecimal price, @JsonProperty("id") long tid,
      @JsonProperty("rate") BigDecimal rate, @JsonProperty("trade_currency") String trade_currency, @JsonProperty("trade_currency_amount") BigDecimal trade_currency_amount,
      @JsonProperty("for_currency") String for_currency, @JsonProperty("for_currency_amount") BigDecimal for_currency_amount) {

    this.amount = amount;
    this.date = date;
    this.price = price;
    this.tid = tid;
    this.rate = rate;
    this.trade_currency = trade_currency;
    this.trade_currency_amount = trade_currency_amount;
    this.for_currency = for_currency;
    this.for_currency_amount = for_currency_amount;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public double getDate() {

    return date;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public long getTid() {

    return tid;
  }

  public BigDecimal getRate() {

    return rate;
  }

  public String getTradeCurrency() {

    return trade_currency;
  }

  public BigDecimal getTradeCurrencyAmount() {

    return trade_currency_amount;
  }

  public String getForCurrency() {

    return for_currency;
  }

  public BigDecimal getForCurrencyAmount() {

    return for_currency_amount;
  }

  @Override
  public String toString() {

    return "VirtExTrades [amount=" + amount + ", date=" + date + ", price=" + price + ", tid=" + tid + ", trade_currency_amount=" + trade_currency_amount + ", rate=" + rate + ", trade_currency="
        + trade_currency + ", for_currency=" + for_currency + ", for_currency_amount=" + for_currency_amount + "]";
  }

}
