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
package com.xeiam.xchange.cryptotrade.dto.trade;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.jackson.CurrencyPairDeserializer;

public class CryptoTradeOrders extends CryptoTradeBaseResponse {

  private final List<CryptoTradeOrder> orders;

  private CryptoTradeOrders(@JsonProperty("data") List<CryptoTradeOrder> orders, @JsonProperty("status") String status, @JsonProperty("error") String error) {

    super(status, error);
    this.orders = orders;
  }

  public List<CryptoTradeOrder> getOrders() {

    return orders;
  }

  @Override
  public String toString() {

    return "CryptoTradeOrders [orders=" + orders + "]";
  }

  public static class CryptoTradeOrder {

    private final long id;
    private final long timestamp;
    private final CurrencyPair currencyPair;
    private final CryptoTradeOrderType type;
    private final BigDecimal initialAmount;
    private final BigDecimal remainingAmount;
    private final BigDecimal rate;
    private final String status;

    private CryptoTradeOrder(@JsonProperty("id") long id, @JsonProperty("timestamp") long timestamp,
        @JsonProperty("pair") @JsonDeserialize(using = CurrencyPairDeserializer.class) CurrencyPair currencyPair, @JsonProperty("type") CryptoTradeOrderType type,
        @JsonProperty("initial_amount") BigDecimal initialAmount, @JsonProperty("remaining_amount") BigDecimal remainingAmount, @JsonProperty("rate") BigDecimal rate,
        @JsonProperty("status") String status) {

      this.id = id;
      this.timestamp = timestamp;
      this.currencyPair = currencyPair;
      this.type = type;
      this.initialAmount = initialAmount;
      this.remainingAmount = remainingAmount;
      this.rate = rate;
      this.status = status;
    }

    public long getId() {

      return id;
    }

    public long getTimestamp() {

      return timestamp;
    }

    public CurrencyPair getCurrencyPair() {

      return currencyPair;
    }

    public CryptoTradeOrderType getType() {

      return type;
    }

    public BigDecimal getInitialAmount() {

      return initialAmount;
    }

    public BigDecimal getRemainingAmount() {

      return remainingAmount;
    }

    public BigDecimal getRate() {

      return rate;
    }

    public String getStatus() {

      return status;
    }

    @Override
    public String toString() {

      return "CryptoTradeOrder [id=" + id + ", timestamp=" + timestamp + ", currencyPair=" + currencyPair + ", type=" + type + ", initialAmount=" + initialAmount + ", remainingAmount="
          + remainingAmount + ", rate=" + rate + ", status=" + status + "]";
    }

  }
}
