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
import java.util.Objects;

import org.joda.money.BigMoney;

import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * <p>
 * DTO representing the exchange order book
 * </p>
 * <p>
 * </p>
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

  public void update(LimitOrder newOrder) {

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

  public void update(OrderBookUpdate newUpdate) {
	  
	  //First, we need to remove orders with the same limit price
	  //Iterators works in a thread safe way
	  Iterator<LimitOrder> it; 
	  if(newUpdate.getLimitOrder().getType() == OrderType.ASK) it = this.asks.iterator();
	  else it = this.bids.iterator();
	  while (it.hasNext()) {
	      LimitOrder order = it.next();
	      if(order.getLimitPrice().compareTo(newUpdate.getLimitOrder().getLimitPrice()) == 0) {
	      	it.remove();
	      }
	  }

	  //If volume is not zero we need to add a new limit order with the updated amount
	  if (newUpdate.getTotalVolume().compareTo(BigDecimal.ZERO) != 0)
	  {	
		  OrderType type = newUpdate.getLimitOrder().getType();
		  BigDecimal tradeableAmount = newUpdate.getTotalVolume();
		  String tradeableIdentifier = newUpdate.getLimitOrder().getTradableIdentifier();
		  String transitionCurrency = newUpdate.getLimitOrder().getTransactionCurrency();
		  String id = newUpdate.getLimitOrder().getId();
		  Date date = newUpdate.getLimitOrder().getTimestamp();
		  BigMoney limit = newUpdate.getLimitOrder().getLimitPrice();
		  LimitOrder updatedOrder = new LimitOrder(type, tradeableAmount, tradeableIdentifier, transitionCurrency, id, date, limit);
		  this.update(updatedOrder);
	  }
  }
  
  @Override
  public String toString() {

    return "Depth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		OrderBook rhs = (OrderBook) obj;
		return Objects.equals(getBids(), rhs.getBids()) && Objects.equals(getAsks(), rhs.getAsks());
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(getBids(),getAsks());
	}
}
