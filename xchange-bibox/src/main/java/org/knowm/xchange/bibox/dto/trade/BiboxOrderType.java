package org.knowm.xchange.bibox.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

/** @author odrotleff */
public enum BiboxOrderType {
  MARKET_ORDER(1),
  LIMIT_ORDER(2);

  private int orderType;

  private BiboxOrderType(int orderType) {
    this.orderType = orderType;
  }

  @JsonCreator
  public static BiboxOrderType fromInt(int orderType) {
    switch (orderType) {
      case 1:
        return MARKET_ORDER;
      case 2:
        return LIMIT_ORDER;
      default:
        throw new RuntimeException("Unexpected Bibox order type!");
    }
  }

  public int asInt() {
    return orderType;
  }
}
