package org.knowm.xchange.dsx.v2.service;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DsxOrderType {
  limit,
  market,
  stopLimit,
  stopMarket;

  @JsonCreator
  public static DsxOrderType getOrderType(String s) {
    try {
      return DsxOrderType.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown order type " + s + ".");
    }
  }
}
