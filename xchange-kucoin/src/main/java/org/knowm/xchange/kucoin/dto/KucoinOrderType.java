package org.knowm.xchange.kucoin.dto;

import org.knowm.xchange.dto.Order.OrderType;

public enum KucoinOrderType {
  BUY(OrderType.BID),
  SELL(OrderType.ASK);

  private OrderType orderType;

  private KucoinOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public static KucoinOrderType fromOrderType(OrderType orderType) {
    if (orderType.equals(OrderType.BID)) {
      return BUY;
    } else if (orderType.equals(OrderType.ASK)) {
      return SELL;
    } else {
      throw new UnsupportedOperationException("Kucoin does not support OrderType " + orderType);
    }
  }

  public OrderType getOrderType() {
    return orderType;
  }
}
