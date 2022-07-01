package org.knowm.xchange.lykke.dto.trade;

import org.knowm.xchange.dto.Order;

public enum LykkeOrderType {
  BUY(Order.OrderType.BID),
  SELL(Order.OrderType.ASK);

  private Order.OrderType orderType;

  private LykkeOrderType(Order.OrderType orderType) {
    this.orderType = orderType;
  }

  public static LykkeOrderType fromOrderType(Order.OrderType orderType) {
    if (orderType.equals(Order.OrderType.BID)) {
      return BUY;
    } else if (orderType.equals(Order.OrderType.ASK)) {
      return SELL;
    } else {
      throw new UnsupportedOperationException("Lykke does not support OrderType " + orderType);
    }
  }

  public Order.OrderType getOrderType() {
    return orderType;
  }
}
