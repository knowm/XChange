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
package com.xeiam.xchange.vaultofsatoshi.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing depth from VaultOfSatoshi
 */

public final class VaultOfSatoshiDepth {

  private final List<VosOrder> asks;
  private final List<VosOrder> bids;
  private final long timestamp;
  private final String order_currency;
  private final String payment_currency;

  @JsonCreator
  public VaultOfSatoshiDepth(@JsonProperty("asks") List<VosOrder> asks, @JsonProperty("bids") List<VosOrder> bids, @JsonProperty("timestamp") long timestamp,
      @JsonProperty("order_currency") String order_currency, @JsonProperty("payment_currency") String payment_currency) {

    this.asks = asks;
    this.bids = bids;
    this.timestamp = timestamp;
    this.order_currency = order_currency;
    this.payment_currency = payment_currency;
  }

  public List<VosOrder> getAsks() {

    return asks;
  }

  public List<VosOrder> getBids() {

    return bids;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public String getPaymentCurrency() {

    return payment_currency;
  }

  public String getOrderCurrency() {

    return order_currency;
  }

  @Override
  public String toString() {

    return "VaultOfSatoshiDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}
