package org.knowm.xchange.hitbtc.v2.service;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum HitbtcOrderType {
  limit,
  market,
  stopLimit,
  stopMarket;

  @JsonCreator
  public static HitbtcOrderType getOrderType(String s) {
    try {
      return HitbtcOrderType.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown order type " + s + ".");
    }
  }
}
