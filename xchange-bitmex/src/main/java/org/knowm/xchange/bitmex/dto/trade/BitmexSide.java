package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BitmexSide {
  BUY("Buy"),

  SELL("Sell");

  @JsonValue
  private final String value;
}
