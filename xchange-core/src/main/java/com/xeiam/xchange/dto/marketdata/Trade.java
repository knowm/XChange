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
package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.money.BigMoney;

import com.xeiam.xchange.dto.Order.OrderType;

/**
 * Data object representing a Trade
 */
public final class Trade implements Comparable<Trade> {

  /**
   * Did this trade result from the execution of a bid or a ask?
   */
  private final OrderType type;

  /**
   * Amount that was traded
   */
  private final BigDecimal tradableAmount;

  /**
   * An identifier that uniquely identifies the tradable
   */
  private final String tradableIdentifier;

  /**
   * The currency used to settle the market order transaction
   */
  private final String transactionCurrency;

  /**
   * The price
   */
  private final BigMoney price;

  /**
   * The timestamp of the trade
   */
  private final Date timestamp;

  /**
   * The trade id
   */
  private final String id;
  
  /**
   * The id of the order responsible for execution of this trade
   */
  private final String orderId;

  /**
   * @param type
   *          The trade type (BID side or ASK side)
   * @param tradableAmount
   *          The depth of this trade
   * @param tradableIdentifier
   *          The exchange identifier (e.g. "BTC/USD")
   * @param transactionCurrency
   *          The transaction currency (e.g. USD in BTC/USD)
   * @param price
   *          The price (either the bid or the ask)
   * @param timestamp
   *          The timestamp when the order was placed. Exchange matching is
   *          usually price first then timestamp asc to clear older orders
   * @param id
   *          The id of the trade
   */
  public Trade(OrderType type, BigDecimal tradableAmount, String tradableIdentifier, String transactionCurrency, BigMoney price, Date timestamp, long id) {
    this(type, tradableAmount, tradableIdentifier, transactionCurrency, price, timestamp, String.valueOf(id), null);
  }
  
  /**
   * @param type
   *          The trade type (BID side or ASK side)
   * @param tradableAmount
   *          The depth of this trade
   * @param tradableIdentifier
   *          The exchange identifier (e.g. "BTC/USD")
   * @param transactionCurrency
   *          The transaction currency (e.g. USD in BTC/USD)
   * @param price
   *          The price (either the bid or the ask)
   * @param timestamp
   *          The timestamp when the order was placed. Exchange matching is
   *          usually price first then timestamp asc to clear older orders
   * @param id
   *          The id of the trade
   */
  public Trade(OrderType type, BigDecimal tradableAmount, String tradableIdentifier, String transactionCurrency, BigMoney price, Date timestamp, String id, String orderId) {

    this.type = type;
    this.tradableAmount = tradableAmount;
    this.tradableIdentifier = tradableIdentifier;
    this.transactionCurrency = transactionCurrency;
    this.price = price;
    this.timestamp = timestamp;
    this.id = id;
    this.orderId = orderId;
  }

  public OrderType getType() {

    return type;
  }

  public BigDecimal getTradableAmount() {

    return tradableAmount;
  }

  public String getTradableIdentifier() {

    return tradableIdentifier;
  }

  public String getTransactionCurrency() {

    return transactionCurrency;
  }

  public BigMoney getPrice() {

    return price;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public String getId() {

    return id;
  }

  @Override
  public String toString() {

    return "Trade [type=" + type + ", tradableAmount=" + tradableAmount + ", tradableIdentifier=" + tradableIdentifier + ", transactionCurrency=" + transactionCurrency + ", price=" + price
        + ", timestamp=" + timestamp + ", id=" + id + ", orderId=" + orderId + "]";
  }

  @Override
  public int compareTo(Trade trade) {
    // NOTE: see https://github.com/timmolter/XChange/issues/225
    return getId().compareTo(trade.getId());
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return this.id.equals(((Trade) o).getId());
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

}
