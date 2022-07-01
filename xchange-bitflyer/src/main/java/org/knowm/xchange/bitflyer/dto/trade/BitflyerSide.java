package org.knowm.xchange.bitflyer.dto.trade;

import org.knowm.xchange.dto.Order;

public enum BitflyerSide {
  BUY,
  SELL;

  public static BitflyerSide fromOrderType(Order.OrderType orderType) {
    return orderType == Order.OrderType.ASK ? SELL : BUY;
  }
}
