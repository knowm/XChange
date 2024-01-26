package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BybitOrderType {
  MARKET("Market"),

  LIMIT("Limit");

  @JsonValue private final String value;
}
