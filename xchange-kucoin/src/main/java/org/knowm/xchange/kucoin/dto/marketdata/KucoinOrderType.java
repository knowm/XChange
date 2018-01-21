package org.knowm.xchange.kucoin.dto.marketdata;

import org.knowm.xchange.dto.Order.OrderType;

public enum KucoinOrderType {
  BUY(OrderType.BID),
  SELL(OrderType.ASK);
  
  private OrderType orderType;

  private KucoinOrderType(OrderType orderType) {
    this.orderType = orderType;
  }
  public OrderType getOrderType() {
    return orderType;
  }
}
