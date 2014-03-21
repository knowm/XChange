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
package com.xeiam.xchange.cryptotrade.dto.trade;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;

public class CryptoTradePlaceOrderReturn extends CryptoTradeBaseResponse {

  private final CryptoTradePlacedOrderData placedOrderData;

  private CryptoTradePlaceOrderReturn(@JsonProperty("data") CryptoTradePlacedOrderData placedOrderData, @JsonProperty("status") String status, @JsonProperty("error") String error) {

    super(status, error);
    this.placedOrderData = placedOrderData;
  }

  public BigDecimal getBought() {

    return placedOrderData.getBought();
  }

  public BigDecimal getRemaining() {

    return placedOrderData.getRemaining();
  }

  public int getOrderId() {

    return placedOrderData.getOrderId();
  }

  public Map<String, BigDecimal> getFunds() {

    return placedOrderData.getFunds();
  }

  @Override
  public String toString() {

    return "CryptoTradePlaceOrderReturn [placedOrderData=" + placedOrderData + "]";
  }

  private static class CryptoTradePlacedOrderData {

    private final BigDecimal bought;
    private final BigDecimal remaining;
    private final int orderId;
    private final Map<String, BigDecimal> funds;

    private CryptoTradePlacedOrderData(@JsonProperty("bought") BigDecimal bought, @JsonProperty("remaining") BigDecimal remaining, @JsonProperty("order_id") int orderId,
        @JsonProperty("funds") Map<String, BigDecimal> funds) {

      this.bought = bought;
      this.remaining = remaining;
      this.orderId = orderId;
      this.funds = funds;
    }

    public BigDecimal getBought() {

      return bought;
    }

    public BigDecimal getRemaining() {

      return remaining;
    }

    public int getOrderId() {

      return orderId;
    }

    public Map<String, BigDecimal> getFunds() {

      return funds;
    }

    @Override
    public String toString() {

      return "CryptoTradePlacedOrderData [bought=" + bought + ", remaining=" + remaining + ", orderId=" + orderId + ", funds=" + funds + "]";
    }

  }
}
