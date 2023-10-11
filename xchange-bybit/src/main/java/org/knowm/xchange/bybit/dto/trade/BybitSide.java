package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BybitSide {
  BUY("Buy"),

  SELL("Sell"),

  NONE("None");

  @JsonValue private final String value;
}
