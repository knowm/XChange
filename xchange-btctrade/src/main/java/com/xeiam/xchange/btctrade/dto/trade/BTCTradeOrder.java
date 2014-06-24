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
package com.xeiam.xchange.btctrade.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCTradeOrder {

  private final String id;
  private final String datetime;
  private final String type;
  private final BigDecimal price;
  private final BigDecimal amountOriginal;
  private final BigDecimal amountOutstanding;

  private final String status;
  private final BTCTradeTrade[] trades;

  public BTCTradeOrder(
      @JsonProperty("id") String id,
      @JsonProperty("datetime") String datetime,
      @JsonProperty("type") String type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount_original") BigDecimal amountOriginal,
      @JsonProperty("amount_outstanding") BigDecimal amountOutstanding,
      @JsonProperty("status") String status,
      @JsonProperty("trades") BTCTradeTrade[] trades) {
    this.id = id;
    this.datetime = datetime;
    this.type = type;
    this.price = price;
    this.amountOriginal = amountOriginal;
    this.amountOutstanding = amountOutstanding;

    this.status = status;
    this.trades = trades;
  }

  public String getId() {
    return id;
  }

  public String getDatetime() {
    return datetime;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmountOriginal() {
    return amountOriginal;
  }

  public BigDecimal getAmountOutstanding() {
    return amountOutstanding;
  }

  public String getStatus() {
    return status;
  }

  public BTCTradeTrade[] getTrades() {
    return trades;
  }

}
