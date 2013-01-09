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
package com.xeiam.xchange.dto.trade;

import java.math.BigDecimal;

import org.joda.money.BigMoney;

import com.xeiam.xchange.dto.Order;

/**
 * Data object representing a limit order
 * 
 * @immutable
 */
public final class LimitOrder extends Order implements Comparable<LimitOrder> {

  /**
   * The limit price
   */
  private final BigMoney limitPrice;

  /**
   * Constructor
   * 
   * @param type
   * @param tradableAmount
   * @param tradableIdentifier
   * @param transactionCurrency
   * @param id
   * @param limitPrice
   */
  public LimitOrder(OrderType type, BigDecimal tradableAmount, String tradableIdentifier, String transactionCurrency, String id, BigMoney limitPrice) {

    super(type, tradableAmount, tradableIdentifier, transactionCurrency, id);
    this.limitPrice = limitPrice;
  }

  /**
   * Constructor
   * 
   * @param type
   * @param tradableAmount
   * @param tradableIdentifier
   * @param transactionCurrency
   * @param id
   * @param limitPrice
   */
  public LimitOrder(OrderType type, BigDecimal tradableAmount, String tradableIdentifier, String transactionCurrency, BigMoney limitPrice) {

    super(type, tradableAmount, tradableIdentifier, transactionCurrency, "");
    this.limitPrice = limitPrice;
  }

  public BigMoney getLimitPrice() {

    return limitPrice;
  }

  @Override
  public String toString() {

    return "LimitOrder [limitPrice=" + limitPrice + ", " + super.toString() + "]";
  }

  @Override
  public int compareTo(LimitOrder limitOrder) {

    return this.getLimitPrice().getAmount().compareTo(limitOrder.getLimitPrice().getAmount()) * (getType() == OrderType.BID ? -1 : 1);

  }

}
