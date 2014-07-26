/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.btcchina.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaIcebergOrder {

  private final long id;
  private final String type;
  private final BigDecimal price;
  private final String market;
  private final BigDecimal amount;
  private final BigDecimal amountOriginal;
  private final BigDecimal disclosedAmount;
  private final BigDecimal variance;
  private final long date;
  private final String status;
  private final BTCChinaOrder[] orders;

  public BTCChinaIcebergOrder(@JsonProperty("id") long id, @JsonProperty("type") String type, @JsonProperty("price") BigDecimal price, @JsonProperty("market") String market,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("amount_original") BigDecimal amountOriginal, @JsonProperty("disclosed_amount") BigDecimal disclosedAmount,
      @JsonProperty("variance") BigDecimal variance, @JsonProperty("date") long date, @JsonProperty("status") String status, @JsonProperty("order") BTCChinaOrder[] orders) {

    super();
    this.id = id;
    this.type = type;
    this.price = price;
    this.market = market;
    this.amount = amount;
    this.amountOriginal = amountOriginal;
    this.disclosedAmount = disclosedAmount;
    this.variance = variance;
    this.date = date;
    this.status = status;
    this.orders = orders;
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

  public String getMarket() {

    return market;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getAmountOriginal() {

    return amountOriginal;
  }

  public BigDecimal getDisclosedAmount() {

    return disclosedAmount;
  }

  public BigDecimal getVariance() {

    return variance;
  }

  public long getDate() {

    return date;
  }

  public String getStatus() {

    return status;
  }

  public BTCChinaOrder[] getOrders() {

    return orders;
  }

}
