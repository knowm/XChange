package org.knowm.xchange.bitso.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Order status enum */
public enum BitsoOrderStatus {
  @JsonProperty("queued")
  QUEUED,

  @JsonProperty("open")
  OPEN,

  @JsonProperty("partially filled")
  PARTIALLY_FILLED;

  @JsonCreator
  public static BitsoOrderStatus fromValue(String value) {
    switch (value) {
      case "queued":
        return QUEUED;
      case "open":
        return OPEN;
      case "partially filled":
        return PARTIALLY_FILLED;
      default:
        throw new IllegalArgumentException("Unknown order status: " + value);
    }
  }
}
