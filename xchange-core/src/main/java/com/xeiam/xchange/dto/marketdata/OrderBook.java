package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * DTO representing the exchange order book
 */
public final class OrderBook {

  private Date timeStamp;
  private final List<LimitOrder> asks;
  private final List<LimitOrder> bids;

  /**
   * Constructor
   *
   * @param timeStamp The timeStamp of the OrderBook or of the latest Update
   * @param asks
   *          The ASK orders
   * @param bids
   *          The BID orders
   */
  public OrderBook(Date timeStamp, List<LimitOrder> asks, List<LimitOrder> bids) {

    this.timeStamp = timeStamp;
    this.asks = asks;
    this.bids = bids;
  }

  public Date getTimeStamp() {

    return timeStamp;
  }

  public List<LimitOrder> getAsks() {

    return asks;
  }

  public List<LimitOrder> getBids() {

    return bids;
  }

  public List<LimitOrder> getOrders(OrderType type) {

    return type == OrderType.ASK ? asks : bids;
  }

  /**
   * Given a new LimitOrder, it will replace and old matching limit order in
   * the orderbook or simply get added. The timeStamp may be updated as well.
   *
   * @param limitOrder the new LimitOrder
   */
  public void update(LimitOrder limitOrder) {

    update(getOrders(limitOrder.getType()), limitOrder);
    updateDate(limitOrder.getTimestamp());
  }

  private void update(List<LimitOrder> asks, LimitOrder limitOrder) {

    int idx = Collections.binarySearch(asks, limitOrder);
    if (idx >= 0) {
      asks.remove(idx);
      asks.add(idx, limitOrder);
    }
    else {
      asks.add(-idx - 1, limitOrder);
    }
  }

  /**
   * Given an OrderBookUpdate, it will replace and old matching limit order in
   * the orderbook or simply get added. The timeStamp may be updated as well.
   *
   * @param orderBookUpdate the new OrderBookUpdate
   */
  public void update(OrderBookUpdate orderBookUpdate) {

    LimitOrder limitOrder = orderBookUpdate.getLimitOrder();
    List<LimitOrder> limitOrders = getOrders(limitOrder.getType());
    int idx = Collections.binarySearch(limitOrders, limitOrder);
    if (idx >= 0) {
      limitOrders.remove(idx);
    }
    else {
      idx = -idx - 1;
    }

    if (orderBookUpdate.getTotalVolume().compareTo(BigDecimal.ZERO) != 0) {
      LimitOrder updatedOrder = withAmount(limitOrder, orderBookUpdate.getTotalVolume());
      limitOrders.add(idx, updatedOrder);
    }

    updateDate(limitOrder.getTimestamp());
  }

  private static LimitOrder withAmount(LimitOrder limitOrder, BigDecimal tradeableAmount) {

    OrderType type = limitOrder.getType();
    CurrencyPair currencyPair = limitOrder.getCurrencyPair();
    String id = limitOrder.getId();
    Date date = limitOrder.getTimestamp();
    BigDecimal limit = limitOrder.getLimitPrice();
    return new LimitOrder(type, tradeableAmount, currencyPair, id, date, limit);
  }

  private void updateDate(Date updateDate) {

    if (updateDate != null && (timeStamp == null || updateDate.after(timeStamp))) {
      this.timeStamp = updateDate;
    }
  }

  @Override
  public int hashCode() {

    int hash = 17;
    hash = 31 * hash + (this.timeStamp != null ? this.timeStamp.hashCode() : 0);
    for (LimitOrder order : this.bids) {
      hash = 31 * hash + order.hashCode();
    }
    for (LimitOrder order : this.asks) {
      hash = 31 * hash + order.hashCode();
    }
    return hash;
  }

  @Override
  public boolean equals(final Object obj) {

    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final OrderBook other = (OrderBook) obj;
    if (this.timeStamp == null ? other.timeStamp != null : !this.timeStamp.equals(other.timeStamp)) {
      return false;
    }
    if (this.bids.size() != other.bids.size()) {
      return false;
    }
    for (int index = 0; index < this.bids.size(); index++) {
      if (!this.bids.get(index).equals(other.bids.get(index))) {
        return false;
      }
    }
    if (this.asks.size() != other.asks.size()) {
      return false;
    }
    for (int index = 0; index < this.asks.size(); index++) {
      if (!this.asks.get(index).equals(other.asks.get(index))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {

    return "OrderBook [timestamp: " + timeStamp + ", asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }
}
