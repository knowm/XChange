package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ContingencyType {
  OCO;

  @JsonCreator
  public static ContingencyType getContingencyType(String s) {
    try {
      return ContingencyType.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown contingency type " + s + ".");
    }
  }
}
