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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.joda.money.BigMoney;

import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * DTO representing the exchange order book
 */
public final class OrderBook {

  private final List<LimitOrder> asks;
  private final List<LimitOrder> bids;

  /**
   * Constructor
   * 
   * @param asks The ASK orders
   * @param bids The BID orders
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

  /**
   * Given a new LimitOrder, it will replace and old matching limit order in the orderbook or simply get added. Finally, it is sorted.
   * 
   * @param limitOrder
   */
  public void update(LimitOrder limitOrder) {

    if (limitOrder.getType().equals(OrderType.ASK)) {

      Iterator<LimitOrder> it = asks.iterator();
      while (it.hasNext()) {
        LimitOrder order = it.next();
        if (order.getLimitPrice().compareTo(limitOrder.getLimitPrice()) == 0) { // they are equal. found it!
          it.remove();
          break;
        }
      }
      asks.add(limitOrder); // just add it
      Collections.sort(asks); // finally sort

    }
    else {

      Iterator<LimitOrder> it = bids.iterator();
      while (it.hasNext()) {
        LimitOrder order = it.next();
        if (order.getLimitPrice().compareTo(limitOrder.getLimitPrice()) == 0) { // they are equal. found it!
          it.remove();
          break;
        }
      }
      bids.add(limitOrder); // just add it
      Collections.sort(bids); // finally sort
    }
  }

  /**
   * Given an OrderBookUpdate, it will replace and old matching limit order in the orderbook or simply get added. Finally, it is sorted.
   * 
   * @param limitOrder
   */
  public void update(OrderBookUpdate orderBookUpdate) {

    // First, we need to remove orders with the same limit price
    Iterator<LimitOrder> it;
    if (orderBookUpdate.getLimitOrder().getType() == OrderType.ASK) {
      it = this.asks.iterator();
    }
    else {
      it = this.bids.iterator();
    }
    while (it.hasNext()) {
      LimitOrder order = it.next();
      if (order.getLimitPrice().compareTo(orderBookUpdate.getLimitOrder().getLimitPrice()) == 0) { // they are equal. found it!
        it.remove();
        break;
      }
    }

    // If volume is not zero we need to add a new limit order with the updated amount
    if (orderBookUpdate.getTotalVolume().compareTo(BigDecimal.ZERO) != 0) {

      OrderType type = orderBookUpdate.getLimitOrder().getType();
      BigDecimal tradeableAmount = orderBookUpdate.getTotalVolume();
      String tradeableIdentifier = orderBookUpdate.getLimitOrder().getTradableIdentifier();
      String transitionCurrency = orderBookUpdate.getLimitOrder().getTransactionCurrency();
      String id = orderBookUpdate.getLimitOrder().getId();
      Date date = orderBookUpdate.getLimitOrder().getTimestamp();
      BigMoney limit = orderBookUpdate.getLimitOrder().getLimitPrice();
      LimitOrder updatedOrder = new LimitOrder(type, tradeableAmount, tradeableIdentifier, transitionCurrency, id, date, limit);

      if (orderBookUpdate.getLimitOrder().getType() == OrderType.ASK) {
        asks.add(updatedOrder);
        Collections.sort(asks);
      }
      else {
        bids.add(updatedOrder);
        Collections.sort(bids);
      }
    }
  }

  @Override
  public String toString() {

    return "Depth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}
