package org.knowm.xchange.cobinhood.dto;

import org.knowm.xchange.dto.Order.OrderType;

public enum CobinhoodOrderSide {
  ask(OrderType.ASK),
  bid(OrderType.BID);

  private final OrderType orderType;

  CobinhoodOrderSide(OrderType orderType) {
    this.orderType = orderType;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public static CobinhoodOrderSide fromOrderType(OrderType type) {
    for (CobinhoodOrderSide side : values()) {
      if (side.getOrderType() == type) {
        return side;
      }
    }
    throw new IllegalArgumentException("Unsupported order type: " + type);
  }
}
