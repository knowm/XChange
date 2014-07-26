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
package com.xeiam.xchange.cryptotrade.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;

public class CryptoTradePublicTrade {

  private final long id;
  private final long timestamp;
  private final String assetPair;
  private final CryptoTradeOrderType type;
  private final BigDecimal orderAmount;
  private final BigDecimal rate;

  public CryptoTradePublicTrade(@JsonProperty("id") long id, @JsonProperty("timestamp") long timestamp, @JsonProperty("pair") String assetPair, @JsonProperty("type") CryptoTradeOrderType type, @JsonProperty("amount") BigDecimal orderAmount,
      @JsonProperty("rate") BigDecimal rate) {

    this.id = id;
    this.timestamp = timestamp;
    this.assetPair = assetPair;
    this.type = type;
    this.orderAmount = orderAmount;
    this.rate = rate;
  }

  public long getId() {

    return id;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public String getAssetPair() {

    return assetPair;
  }

  public CryptoTradeOrderType getType() {

    return type;
  }

  public BigDecimal getOrderAmount() {

    return orderAmount;
  }

  public BigDecimal getRate() {

    return rate;
  }

  @Override
  public String toString() {

    return "CryptoTradePublicTrade [id=" + id + ", timestamp=" + timestamp + ", assetPair=" + assetPair + ", type=" + type + ", orderAmount=" + orderAmount + ", rate=" + rate + "]";
  }
}
