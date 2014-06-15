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
package com.xeiam.xchange.anx.v2.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class ANXTrade {

  private final BigDecimal amount;
  private final long amountInt;
  private final String item;
  private final String priceCurrency;
  private final BigDecimal price;
  private final long priceInt;
  private final String primary;
  private final String properties;
  private final long tid;
  private final String tradeType;

  public ANXTrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("amount_int") long amountInt, @JsonProperty("item") String item, @JsonProperty("price_currency") String priceCurrency,
      @JsonProperty("price") BigDecimal price, @JsonProperty("price_int") long priceInt, @JsonProperty("primary") String primary, @JsonProperty("properties") String properties,
      @JsonProperty("tid") long tid, @JsonProperty("trade_type") String tradeType) {

    this.amount = amount;
    this.amountInt = amountInt;
    this.item = item;
    this.priceCurrency = priceCurrency;
    this.price = price;
    this.priceInt = priceInt;
    this.primary = primary;
    this.properties = properties;
    this.tid = tid;
    this.tradeType = tradeType;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getAmountInt() {

    return amountInt;
  }

  public String getItem() {

    return item;
  }

  public String getPriceCurrency() {

    return priceCurrency;
  }

  public BigDecimal getPrice() {

    return price;
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

    return "ANXTrade [amount=" + amount + ", amountInt=" + amountInt + ", item=" + item + ", priceCurrency=" + priceCurrency + ", price=" + price + ", priceInt=" + priceInt + ", primary=" + primary
        + ", properties=" + properties + ", tid=" + tid + ", tradeType=" + tradeType + "]";
  }

}
