package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderType {
  LIMIT, MARKET, STOP_LOSS, STOP_LOSS_LIMIT;

  @JsonCreator
  public static OrderType getOrderType(String s) {
    try {
      return OrderType.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown order type " + s + ".");
    }
  }
}
