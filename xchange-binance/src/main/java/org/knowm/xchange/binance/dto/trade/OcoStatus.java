package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OcoStatus {
  RESPONSE,
  EXEC_STARTED,
  ALL_DONE;

  @JsonCreator
  public static OcoStatus getOcoStatus(String s) {
    try {
      return OcoStatus.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown OCO status " + s + ".");
    }
  }
}
