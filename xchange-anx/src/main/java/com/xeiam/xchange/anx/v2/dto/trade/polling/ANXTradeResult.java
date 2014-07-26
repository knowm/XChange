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
package com.xeiam.xchange.anx.v2.dto.trade.polling;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Data object representing trades from ANX
 */
public final class ANXTradeResult {

  private final String tradeId;
  private final String orderId;
  private final Date timestamp;
  private final BigDecimal tradedCurrencyFillAmount;
  private final BigDecimal settlementCurrencyFillAmount;
  private final String currencyPair;
  private final String side;


  public ANXTradeResult(@JsonProperty("tradeId") String tradeId, @JsonProperty("orderId") String orderId, @JsonProperty("timestamp") Date timestamp, @JsonProperty("tradedCurrencyFillAmount") BigDecimal tradedCurrencyFillAmount, @JsonProperty("settlementCurrencyFillAmount") BigDecimal settlementCurrencyFillAmount, @JsonProperty("ccyPair") String currencyPair, @JsonProperty("side") String side) {
    this.tradeId = tradeId;
    this.orderId = orderId;
    this.timestamp = timestamp;
    this.tradedCurrencyFillAmount = tradedCurrencyFillAmount;
    this.settlementCurrencyFillAmount = settlementCurrencyFillAmount;
    this.currencyPair = currencyPair;
    this.side = side;
  }

  public String getTradeId() {
    return tradeId;
  }

  public String getOrderId() {
    return orderId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public BigDecimal getTradedCurrencyFillAmount() {
    return tradedCurrencyFillAmount;
  }

  public BigDecimal getSettlementCurrencyFillAmount() {
    return settlementCurrencyFillAmount;
  }

  public String getCurrencyPair() {
    return currencyPair;
  }

  public String getSide() {
    return side;
  }

  @Override
  public String toString() {
    return "ANXOrderResultTrade{" +
            "tradeId='" + tradeId + '\'' +
            ", orderId='" + orderId + '\'' +
            ", timestamp=" + timestamp +
            ", tradedCurrencyFillAmount=" + tradedCurrencyFillAmount +
            ", settlementCurrencyFillAmount=" + settlementCurrencyFillAmount +
            ", currencyPair='" + currencyPair + '\'' +
            ", side='" + side + '\'' +
            '}';
  }
}
