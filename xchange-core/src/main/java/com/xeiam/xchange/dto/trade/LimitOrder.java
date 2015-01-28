package com.xeiam.xchange.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;

/**
 * <p>
 * DTO representing a limit order
 * </p>
 * <p>
 * A limit order lets you set a minimum or maximum price before your trade will be treated by the exchange as a {@link MarketOrder}. There is no
 * guarantee that your conditions will be met on the exchange, so your order may not be executed. However, until you become very experienced, almost
 * all orders should be limit orders to protect yourself.
 * </p>
 */
public final class LimitOrder extends Order implements Comparable<LimitOrder> {

  /**
   * The limit price
   */
  private final BigDecimal limitPrice;

  /**
   * @param type Either BID (buying) or ASK (selling)
   * @param tradableAmount The amount to trade
   * @param currencyPair The identifier (e.g. BTC/USD)
   * @param id An id (usually provided by the exchange)
   * @param timestamp a Date object representing the order's timestamp
   * @param limitPrice In a BID this is the highest acceptable price, in an ASK this is the lowest acceptable price
   */
  public LimitOrder(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, String id, Date timestamp, BigDecimal limitPrice) {

    super(type, tradableAmount, currencyPair, id, timestamp);
    this.limitPrice = limitPrice;
  }

  /**
   * @return The limit price
   */
  public BigDecimal getLimitPrice() {

    return limitPrice;
  }

  @Override
  public String toString() {

    return "LimitOrder [limitPrice=" + limitPrice + ", " + super.toString() + "]";
  }

  @Override
  public int compareTo(LimitOrder limitOrder) {

    return this.getLimitPrice().compareTo(limitOrder.getLimitPrice()) * (getType() == OrderType.BID ? -1 : 1);
  }

  @Override
  public int hashCode() {

    int hash = super.hashCode();
    hash = 59 * hash + (this.limitPrice != null ? this.limitPrice.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final LimitOrder other = (LimitOrder) obj;
    if (this.limitPrice == null ? (other.limitPrice != null) : this.limitPrice.compareTo(other.limitPrice) != 0) {
      return false;
    }
    return super.equals(obj);
  }

  public static class Builder {

    OrderType orderType;
    BigDecimal tradableAmount;
    CurrencyPair currencyPair;
    String id;
    Date timestamp;
    BigDecimal limitPrice;

    public Builder(OrderType orderType, CurrencyPair currencyPair) {

      this.orderType = orderType;
      this.currencyPair = currencyPair;
    }

    public static Builder from(LimitOrder order) {

      return from((Order) order).limitPrice(order.getLimitPrice());
    }

    public static Builder from(Order o) {
      return new Builder(o.getType(), o.getCurrencyPair()).tradableAmount(o.getTradableAmount()).timestamp(o.getTimestamp()).id(o.getId());
    }

    public Builder orderType(OrderType orderType) {

      this.orderType = orderType;
      return this;
    }

    public Builder tradableAmount(BigDecimal tradableAmount) {

      this.tradableAmount = tradableAmount;
      return this;
    }

    public Builder currencyPair(CurrencyPair currencyPair) {

      this.currencyPair = currencyPair;
      return this;
    }

    public Builder id(String id) {

      this.id = id;
      return this;
    }

    public Builder timestamp(Date timestamp) {

      this.timestamp = timestamp;
      return this;
    }

    public Builder limitPrice(BigDecimal limitPrice) {

      this.limitPrice = limitPrice;
      return this;
    }

    public LimitOrder build() {

      return new LimitOrder(orderType, tradableAmount, currencyPair, id, timestamp, limitPrice);
    }

  }
}
