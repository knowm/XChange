package org.knowm.xchange.latoken.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LatokenOrderStatus {
  active,
  partiallyFilled,
  filled,
  canceling,
  cancelled;

  @JsonCreator
  public static LatokenOrderStatus parse(String s) {
    try {
      return LatokenOrderStatus.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown LatokenOrderStatus " + s + ".");
    }
  }
}
