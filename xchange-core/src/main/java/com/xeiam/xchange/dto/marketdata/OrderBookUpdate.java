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
 * Immutable data object representing a Market Depth update.
 */
public final class OrderBookUpdate {

  private final LimitOrder limitOrder;

  /** this is the total volume at this price in the order book */
  private final BigDecimal totalVolume;

  /**
   * Build an order book update.
   * 
   * @param type                the order type (BID/ASK)
   * @param volume              volume in the tradable currency
   * @param tradableIdentifier  the tradable identifier (e.g. BTC in BTC/USD)
   * @param transactionCurrency the transaction currency (e.g. USD in BTC/USD)
   * @param limitPrice          the limit price - minimum acceptable price for a BID, maximum acceptable price for an ASK
   * @param timestamp           the timestamp for the update
   * @param totalVolume         the total volume in the order
   */
  // todo: document the distinction between volume and total volume, and which currencies they are in respectively
  // todo: document which currency the limitPrice is in
  public OrderBookUpdate(OrderType type, BigDecimal volume, String tradableIdentifier, String transactionCurrency, BigMoney limitPrice, Date timestamp, BigDecimal totalVolume) {

    this.limitOrder = new LimitOrder(type, volume, tradableIdentifier, transactionCurrency, limitPrice, timestamp);
    this.totalVolume = totalVolume;
  }

  /**
   * Get the order limit.
   *
   * @return  the order limit
   */
  public LimitOrder getLimitOrder() {

    return limitOrder;
  }

  /**
   * Get the total volume.
   *
   * @return the total volume
   */
  public BigDecimal getTotalVolume() {

    return totalVolume;
  }

  @Override
  public String toString() {

    return "OrderBookUpdate [limitOrder=" + limitOrder + ", totalVolume=" + totalVolume + "]";
  }

}
