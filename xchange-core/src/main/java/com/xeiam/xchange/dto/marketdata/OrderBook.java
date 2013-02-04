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
package com.xeiam.xchange.dto.marketdata;

import java.util.Collections;
import java.util.List;

import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * Data object representing OrderBook
 */
public final class OrderBook {

  private final List<LimitOrder> asks;
  private final List<LimitOrder> bids;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  public OrderBook(List<LimitOrder> asks, List<LimitOrder> bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public List<LimitOrder> getAsks() {

    Collections.sort(asks);
    return asks;
  }

  public List<LimitOrder> getBids() {

    Collections.sort(bids);
    return bids;
  }

  public void Update(LimitOrder newOrder) {

    if (newOrder.getType().equals(OrderType.ASK)) {
      int index = asks.indexOf(newOrder);
      if (index > -1)
        asks.set(index, newOrder);
      else
        asks.add(newOrder);
      Collections.sort(asks);
    }
    if (newOrder.getType().equals(OrderType.BID)) {
      int index = bids.indexOf(newOrder);
      if (index > -1)
        bids.set(index, newOrder);
      else
        bids.add(newOrder);
      Collections.sort(bids);
    }

  }

  @Override
  public String toString() {

    return "Depth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}
