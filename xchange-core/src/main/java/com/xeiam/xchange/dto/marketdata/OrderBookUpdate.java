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
 * <p>
 * Can be represented as either a new LimitOrder (newVolume) or a Trade (deltaVolume)
 */
public final class OrderBookUpdate {

  private final LimitOrder limitOrder;
  private final Trade trade;

  /**
   * NEW: The change in volume at limitPrice
   */

  /**
   * Constructor
   * 
   * @param type
   * @param newVolume
   * @param volumeChange
   * @param tradableIdentifier
   * @param totalVolume
   * @param transactionCurrency
   * @param limitPrice
   * @param deltaVolume
   * @param date
   */
  public OrderBookUpdate(OrderType type, BigDecimal newVolume, String tradableIdentifier, String transactionCurrency, long date, BigMoney limitPrice, BigDecimal deltaVolume) {

    this.limitOrder = new LimitOrder(type, newVolume, tradableIdentifier, transactionCurrency, limitPrice);
    this.trade = new Trade(type, deltaVolume, tradableIdentifier, transactionCurrency, limitPrice, new Date(date));
  }

  public LimitOrder asLimitOrder() {

    return limitOrder;
  }

  public Trade asTrade() {

    return trade;
  }

  @Override
  public String toString() {

    return "Change: " + trade.getTradableAmount().toString() + " @ " + limitOrder.toString();
  }

}
