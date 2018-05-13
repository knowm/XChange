package org.knowm.xchange.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

/**
 * DTO representing a stop order
 *
 * <p>A stop order lets you set a minimum or maximum price before your trade will be treated by the
 * exchange as a {@link MarketOrder} unless a limit price is also set. There is no guarantee that
 * your conditions will be met on the exchange, so your order may not be executed.
 */
public class StopOrder extends Order implements Comparable<StopOrder> {

  /** The stop price */
  protected final BigDecimal stopPrice;
  /**
   * The limit price this should be null if the stop order should be treated as a market order once
   * the stop price is hit
   */
  protected BigDecimal limitPrice = null;
  /**
   * @param type Either BID (buying) or ASK (selling)
   * @param originalAmount The amount to trade
   * @param currencyPair The identifier (e.g. BTC/USD)
   * @param id An id (usually provided by the exchange)
   * @param timestamp a Date object representing the order's timestamp according to the exchange's
   *     server, null if not provided
   * @param stopPrice In a BID this is the highest acceptable price, in an ASK this is the lowest
   *     acceptable price
   */
  public StopOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal stopPrice) {

    super(type, originalAmount, currencyPair, id, timestamp);
    this.stopPrice = stopPrice;
  }

  /**
   * @param type Either BID (buying) or ASK (selling)
   * @param originalAmount The amount to trade
   * @param cumulativeAmount The cumulative amount
   * @param currencyPair The identifier (e.g. BTC/USD)
   * @param id An id (usually provided by the exchange)
   * @param timestamp a Date object representing the order's timestamp according to the exchange's
   *     server, null if not provided
   * @param stopPrice In a BID this is the highest acceptable price, in an ASK this is the lowest
   *     acceptable price
   */
  public StopOrder(
      OrderType type,
      BigDecimal originalAmount,
      BigDecimal cumulativeAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal stopPrice) {

    super(
        type,
        originalAmount,
        currencyPair,
        id,
        timestamp,
        BigDecimal.ZERO,
        cumulativeAmount,
        BigDecimal.ZERO,
        OrderStatus.PENDING_NEW);
    this.stopPrice = stopPrice;
  }

  /**
   * @param type Either BID (buying) or ASK (selling)
   * @param originalAmount The amount to trade
   * @param currencyPair The identifier (e.g. BTC/USD)
   * @param id An id (usually provided by the exchange)
   * @param timestamp a Date object representing the order's timestamp according to the exchange's
   *     server, null if not provided
   * @param stopPrice In a BID this is the highest acceptable price, in an ASK this is the lowest
   *     acceptable price
   * @param averagePrice the weighted average price of any fills belonging to the order
   * @param cumulativeAmount the amount that has been filled
   * @param status the status of the order at the exchange or broker
   */
  public StopOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal stopPrice,
      BigDecimal averagePrice,
      BigDecimal cumulativeAmount,
      OrderStatus status) {

    super(
        type,
        originalAmount,
        currencyPair,
        id,
        timestamp,
        averagePrice,
        cumulativeAmount,
        BigDecimal.ZERO,
        status);
    this.stopPrice = stopPrice;
  }

  /**
   * @param type Either BID (buying) or ASK (selling)
   * @param originalAmount The amount to trade
   * @param currencyPair The identifier (e.g. BTC/USD)
   * @param id An id (usually provided by the exchange)
   * @param timestamp a Date object representing the order's timestamp according to the exchange's
   *     server, null if not provided
   * @param stopPrice In a BID this is the highest acceptable price, in an ASK this is the lowest
   *     acceptable price
   * @param limitPrice The limit price the order should be placed at once the stopPrice has been hit
   *     null for market
   * @param averagePrice the weighted average price of any fills belonging to the order
   * @param cumulativeAmount the amount that has been filled
   * @param status the status of the order at the exchange or broker
   */
  public StopOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal stopPrice,
      BigDecimal limitPrice,
      BigDecimal averagePrice,
      BigDecimal cumulativeAmount,
      OrderStatus status) {

    super(
        type,
        originalAmount,
        currencyPair,
        id,
        timestamp,
        averagePrice,
        cumulativeAmount,
        BigDecimal.ZERO,
        status);
    this.stopPrice = stopPrice;
    this.limitPrice = limitPrice;
  }

  /** @return The stop price */
  public BigDecimal getStopPrice() {

    return stopPrice;
  }

  /** @return The limit price */
  public BigDecimal getLimitPrice() {

    return limitPrice;
  }

  @Override
  public String toString() {

    return "StopOrder [stopPrice=" + stopPrice + ", " + super.toString() + "]";
  }

  @Override
  public int compareTo(StopOrder stopOrder) {

    final int ret;

    if (this.getType() == stopOrder.getType()) {
      // Same side
      ret =
          this.getStopPrice().compareTo(stopOrder.getStopPrice())
              * (getType() == OrderType.BID ? -1 : 1);
    } else {
      // Keep bid side be less than ask side
      ret = this.getType() == OrderType.BID ? -1 : 1;
    }

    return ret;
  }

  @Override
  public int hashCode() {

    int hash = super.hashCode();
    hash = 59 * hash + (this.stopPrice != null ? this.stopPrice.hashCode() : 0);
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
    final StopOrder other = (StopOrder) obj;
    if (this.stopPrice == null
        ? (other.stopPrice != null)
        : this.stopPrice.compareTo(other.stopPrice) != 0) {
      return false;
    }
    return super.equals(obj);
  }

  public static class Builder extends Order.Builder {

    protected BigDecimal stopPrice;

    protected BigDecimal limitPrice;

    public Builder(OrderType orderType, CurrencyPair currencyPair) {

      super(orderType, currencyPair);
    }

    public static Builder from(Order order) {

      Builder builder =
          (Builder)
              new Builder(order.getType(), order.getCurrencyPair())
                  .originalAmount(order.getOriginalAmount())
                  .timestamp(order.getTimestamp())
                  .id(order.getId())
                  .flags(order.getOrderFlags())
                  .orderStatus(order.getStatus())
                  .averagePrice(order.getAveragePrice());
      if (order instanceof StopOrder) {
        StopOrder stopOrder = (StopOrder) order;
        builder.stopPrice(stopOrder.getStopPrice());
        builder.limitPrice(stopOrder.getLimitPrice());
      }
      return builder;
    }

    @Override
    public Builder orderType(OrderType orderType) {

      return (Builder) super.orderType(orderType);
    }

    @Override
    public Builder originalAmount(BigDecimal originalAmount) {

      return (Builder) super.originalAmount(originalAmount);
    }

    @Override
    public Builder cumulativeAmount(BigDecimal originalAmount) {

      return (Builder) super.cumulativeAmount(originalAmount);
    }

    public Builder remainingAmount(BigDecimal remainingAmount) {

      return (Builder) super.remainingAmount(remainingAmount);
    }

    @Override
    public Builder currencyPair(CurrencyPair currencyPair) {

      return (Builder) super.currencyPair(currencyPair);
    }

    @Override
    public Builder id(String id) {

      return (Builder) super.id(id);
    }

    @Override
    public Builder timestamp(Date timestamp) {

      return (Builder) super.timestamp(timestamp);
    }

    @Override
    public Builder orderStatus(OrderStatus status) {

      return (Builder) super.orderStatus(status);
    }

    @Override
    public Builder averagePrice(BigDecimal averagePrice) {

      return (Builder) super.averagePrice(averagePrice);
    }

    @Override
    public Builder flag(IOrderFlags flag) {

      return (Builder) super.flag(flag);
    }

    @Override
    public Builder flags(Set<IOrderFlags> flags) {

      return (Builder) super.flags(flags);
    }

    public Builder stopPrice(BigDecimal stopPrice) {

      this.stopPrice = stopPrice;
      return this;
    }

    public Builder limitPrice(BigDecimal limitPrice) {

      this.limitPrice = limitPrice;
      return this;
    }

    public StopOrder build() {

      StopOrder order;
      if (remainingAmount != null) {
        order =
            new StopOrder(
                orderType,
                originalAmount,
                currencyPair,
                id,
                timestamp,
                stopPrice,
                limitPrice,
                averagePrice,
                originalAmount.subtract(remainingAmount),
                status);
      } else {
        order =
            new StopOrder(
                orderType,
                originalAmount,
                currencyPair,
                id,
                timestamp,
                stopPrice,
                limitPrice,
                averagePrice,
                cumulativeAmount,
                status);
      }
      order.setOrderFlags(flags);
      return order;
    }
  }
}
