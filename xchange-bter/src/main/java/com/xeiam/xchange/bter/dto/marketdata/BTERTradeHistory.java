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
package com.xeiam.xchange.bter.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bter.dto.BTERBaseResponse;
import com.xeiam.xchange.bter.dto.BTEROrderType;

public class BTERTradeHistory extends BTERBaseResponse {

  private final List<BTERPublicTrade> trades;
  private final String elapsed;

  private BTERTradeHistory(@JsonProperty("data") List<BTERPublicTrade> trades, @JsonProperty("result") boolean result, @JsonProperty("elapsed") String elapsed) {

    super(result, null);
    this.trades = trades;
    this.elapsed = elapsed;
  }

  public List<BTERPublicTrade> getTrades() {

    return trades;
  }

  public String getElapsed() {

    return elapsed;
  }

  @Override
  public String toString() {

    return "BTERPublicTrades [trades=" + trades + ", elapsed=" + elapsed + "]";
  }

  public static class BTERPublicTrade {

    private final long date;
    private final BigDecimal price;
    private final BigDecimal amount;
    private final String tradeId;
    private final BTEROrderType type;

    private BTERPublicTrade(@JsonProperty("date") long date, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount, @JsonProperty("tid") String tradeId,
        @JsonProperty("type") BTEROrderType type) {

      this.date = date;
      this.price = price;
      this.amount = amount;
      this.tradeId = tradeId;
      this.type = type;
    }

    public long getDate() {

      return date;
    }

    public BigDecimal getPrice() {

      return price;
    }

    public BigDecimal getAmount() {

      return amount;
    }

    public String getTradeId() {

      return tradeId;
    }

    public BTEROrderType getType() {

      return type;
    }

    @Override
    public String toString() {

      return "BTERPublicTrade [date=" + date + ", price=" + price + ", amount=" + amount + ", tradeId=" + tradeId + ", type=" + type + "]";
    }
  }
}
