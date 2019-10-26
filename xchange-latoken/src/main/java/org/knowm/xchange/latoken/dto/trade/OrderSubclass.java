package org.knowm.xchange.latoken.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderSubclass {
  limit;

  @JsonCreator
  public static OrderSubclass parse(String s) {
    try {
      return OrderSubclass.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown OrderSubclass " + s + ".");
    }
  }
}
