package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderType {
  LIMIT,
  MARKET,
  TAKE_PROFIT_LIMIT,
  STOP_LOSS_LIMIT,
  STOP_LOSS,
  TAKE_PROFIT,
  LIMIT_MAKER;

  private OrderType() {}

  @JsonCreator
  public static OrderType getOrderType(String s) {
    try {
      return valueOf(s);
    } catch (Exception var2) {
      throw new RuntimeException("Unknown order type " + s + ".");
    }
  }
}
