package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OcoOrderStatus {
  EXECUTING,
  ALL_DONE,
  REJECT;

  @JsonCreator
  public static OcoOrderStatus getOcoOrderStatus(String s) {
    try {
      return OcoOrderStatus.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown OCO order status " + s + ".");
    }
  }
}
