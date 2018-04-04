package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderSide {
  BUY,
  SELL;

  @JsonCreator
  public static OrderSide getOrderSide(String s) {
    try {
      return OrderSide.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown order side " + s + ".");
    }
  }
}
