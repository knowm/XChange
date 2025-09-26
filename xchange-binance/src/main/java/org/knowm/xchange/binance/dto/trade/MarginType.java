package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MarginType {
  ISOLATED,
  CROSSED;

  @JsonCreator
  public static MarginType getOrderSide(String s) {
    try {
      return MarginType.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown margin type " + s + ".");
    }
  }
}
