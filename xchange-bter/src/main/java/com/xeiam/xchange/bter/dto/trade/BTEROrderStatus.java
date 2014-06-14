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
package com.xeiam.xchange.bter.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bter.BTERAdapters;
import com.xeiam.xchange.bter.dto.BTERBaseResponse;
import com.xeiam.xchange.bter.dto.BTEROrderType;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * Created by David Henry on 2/19/14.
 */
public class BTEROrderStatus extends BTERBaseResponse {

  private final BTEROrderStatusInfo orderStatusInfo;

  private BTEROrderStatus(@JsonProperty("order") BTEROrderStatusInfo orderStatusInfo, @JsonProperty("result") boolean result, @JsonProperty("msg") String msg) {

    super(result, msg);
    this.orderStatusInfo = orderStatusInfo;
  }

  public String getId() {

    return orderStatusInfo.getId();
  }

  public String getStatus() {

    return orderStatusInfo.getStatus();
  }

  public CurrencyPair getCurrencyPair() {

    return orderStatusInfo.getCurrencyPair();
  }

  public BTEROrderType getType() {

    return orderStatusInfo.getType();
  }

  public BigDecimal getRate() {

    return orderStatusInfo.getRate();
  }

  public BigDecimal getAmount() {

    return orderStatusInfo.getAmount();
  }

  public BigDecimal getInitialRate() {

    return orderStatusInfo.getInitialRate();
  }

  public BigDecimal getInitialAmount() {

    return orderStatusInfo.getInitialAmount();
  }

  public String toString() {

    return orderStatusInfo.toString();
  }

  public static class BTEROrderStatusInfo {

    private final String id;
    private final String status;
    private final CurrencyPair currencyPair;
    private final BTEROrderType type;
    private final BigDecimal rate;
    private final BigDecimal amount;
    private final BigDecimal initialRate;
    private final BigDecimal initialAmount;

    private BTEROrderStatusInfo(@JsonProperty("id") String id, @JsonProperty("status") String status, @JsonProperty("pair") String currencyPair, @JsonProperty("type") BTEROrderType type,
        @JsonProperty("rate") BigDecimal rate, @JsonProperty("amount") BigDecimal amount, @JsonProperty("initial_rate") BigDecimal initialRate, @JsonProperty("initial_amount") BigDecimal initialAmount) {

      this.id = id;
      this.status = status;
      this.currencyPair = BTERAdapters.adaptCurrencyPair(currencyPair);
      this.type = type;
      this.rate = rate;
      this.amount = amount;
      this.initialRate = initialRate;
      this.initialAmount = initialAmount;
    }

    public String getId() {

      return id;
    }

    public String getStatus() {

      return status;
    }

    public CurrencyPair getCurrencyPair() {

      return currencyPair;
    }

    public BTEROrderType getType() {

      return type;
    }

    public BigDecimal getRate() {

      return rate;
    }

    public BigDecimal getAmount() {

      return amount;
    }

    public BigDecimal getInitialRate() {

      return initialRate;
    }

    public BigDecimal getInitialAmount() {

      return initialAmount;
    }

    @Override
    public String toString() {

      return "BTEROrderStatusInfo [id=" + id + ", status=" + status + ", currencyPair=" + currencyPair + ", type=" + type + ", rate=" + rate + ", amount=" + amount + ", initialRate=" + initialRate
          + ", initialAmount=" + initialAmount + "]";
    }
  }
}
