package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderResponseType {
  ACK,
  RESULT,
  FULL;

  @JsonCreator
  public static OrderResponseType getOrderResponseType(String s) {
    try {
      return OrderResponseType.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown response type " + s + ".");
    }
  }
}
