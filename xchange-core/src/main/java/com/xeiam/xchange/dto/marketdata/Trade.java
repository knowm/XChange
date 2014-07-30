package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;

/**
 * Data object representing a Trade
 */
public final class Trade {

  /**
   * Did this trade result from the execution of a bid or a ask?
   */
  private final OrderType type;

  /**
   * Amount that was traded
   */
  private final BigDecimal tradableAmount;

  /**
   * The currency pair
   */
  private final CurrencyPair currencyPair;

  /**
   * The price
   */
  private final BigDecimal price;

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
   * @param orderId
   *          The id of the corresponding order responsible for execution of this trade
   */
  public Trade(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, BigDecimal price, Date timestamp, String id, String orderId) {

    this.type = type;
    this.tradableAmount = tradableAmount;
    this.currencyPair = currencyPair;
    this.price = price;
    this.timestamp = timestamp;
    this.id = id;
    this.orderId = orderId;
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
  public Trade(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, BigDecimal price, Date timestamp, String id) {

    this(type, tradableAmount, currencyPair, price, timestamp, id, null);

  }

  public OrderType getType() {

    return type;
  }

  public BigDecimal getTradableAmount() {

    return tradableAmount;
  }

  public CurrencyPair getCurrencyPair() {

    return currencyPair;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public String getId() {

    return id;
  }

  public String getOrderId() {

    return orderId;
  }

  @Override
  public String toString() {

    return "Trade [type=" + type + ", tradableAmount=" + tradableAmount + ", currencyPair=" + currencyPair + ", price=" + price + ", timestamp=" + timestamp + ", id=" + id + ", orderId=" + orderId
        + "]";
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
