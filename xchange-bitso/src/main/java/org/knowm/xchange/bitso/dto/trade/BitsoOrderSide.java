package org.knowm.xchange.bitso.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Order side enum */
public enum BitsoOrderSide {
  @JsonProperty("buy")
  BUY,

  @JsonProperty("sell")
  SELL;

  @JsonCreator
  public static BitsoOrderSide fromValue(String value) {
    switch (value) {
      case "buy":
        return BUY;
      case "sell":
        return SELL;
      default:
        throw new IllegalArgumentException("Unknown order side: " + value);
    }
  }
}
