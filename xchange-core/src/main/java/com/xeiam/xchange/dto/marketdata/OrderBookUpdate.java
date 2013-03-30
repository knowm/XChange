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
import java.util.Date;

import org.joda.money.BigMoney;

import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * Data object representing a Market Depth update
 */
public final class OrderBookUpdate {

  private final LimitOrder limitOrder;

  private final Date timeStamp;

  /** this is the total volume at this price in the order book */
  private final BigDecimal totalVolume;

  /**
   * Constructor
   * 
   * @param type
   * @param volume
   * @param tradableIdentifier
   * @param transactionCurrency
   * @param limitPrice
   * @param date
   * @param totalVolume
   */
  public OrderBookUpdate(OrderType type, BigDecimal volume, String tradableIdentifier, String transactionCurrency, BigMoney limitPrice, Date date, BigDecimal totalVolume) {

    this.limitOrder = new LimitOrder(type, volume, tradableIdentifier, transactionCurrency, limitPrice);
    this.timeStamp = date;
    this.totalVolume = totalVolume;
  }

  public LimitOrder getLimitOrder() {

    return limitOrder;
  }

  public Date getTimeStamp() {

    return timeStamp;
  }

  public BigDecimal getTotalVolume() {

    return totalVolume;
  }

  @Override
  public String toString() {

    return "OrderBookUpdate [limitOrder=" + limitOrder + ", timeStamp=" + timeStamp + ", totalVolume=" + totalVolume + "]";
  }

}
