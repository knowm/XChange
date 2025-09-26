package org.knowm.xchange.bitso.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Order type enum */
public enum BitsoOrderType {
  @JsonProperty("market")
  MARKET,

  @JsonProperty("limit")
  LIMIT;

  @JsonCreator
  public static BitsoOrderType fromValue(String value) {
    switch (value) {
      case "market":
        return MARKET;
      case "limit":
        return LIMIT;
      default:
        throw new IllegalArgumentException("Unknown order type: " + value);
    }
  }
}
