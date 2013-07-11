/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v2.dto.trade.polling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.mtgox.v2.dto.MtGoxValue;

/**
 * Data object representing Open Orders from MtGox
 */
public final class MtGoxOrderResult {

  private final MtGoxValue avgCost;
  private final String orderId;
  private final MtGoxValue totalAmount;
  private final MtGoxValue totalSpent;
  private final MtGoxOrderResultTrade[] trades;

  /**
   * Constructor
   * 
   * @param avgCost
   * @param orderId
   * @param totalAmount
   * @param totalSpent
   * @param trades
   */
  public MtGoxOrderResult(@JsonProperty("avg_cost") MtGoxValue avgCost, @JsonProperty("order_id") String orderId, @JsonProperty("total_amount") MtGoxValue totalAmount,
      @JsonProperty("total_spent") MtGoxValue totalSpent, @JsonProperty("trades") MtGoxOrderResultTrade[] trades) {

    this.avgCost = avgCost;
    this.orderId = orderId;
    this.totalAmount = totalAmount;
    this.totalSpent = totalSpent;
    this.trades = trades;
  }

  public MtGoxValue getAvgCost() {

    return avgCost;
  }

  public String getOrderId() {

    return orderId;
  }

  public MtGoxValue getTotalAmount() {

    return totalAmount;
  }

  public MtGoxValue getTotalSpent() {

    return totalSpent;
  }

  public MtGoxOrderResultTrade[] getTrades() {

    return trades;
  }

  @Override
  public String toString() {

    String tradesString = "[";
    for (int i = 0; i < trades.length; i++)
      tradesString += ((i > 0) ? ", " : "") + trades[i].toString();
    tradesString += "]";
    return "MtGoxOpenOrder [avgCost=" + avgCost + ", orderId=" + orderId + ", totalAmount=" + totalAmount + ", totalSpent=" + totalSpent + ", trades=" + tradesString + "]";
  }

}
