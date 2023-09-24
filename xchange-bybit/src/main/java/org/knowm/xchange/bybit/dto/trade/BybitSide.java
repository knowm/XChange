package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.dto.Order.OrderType;

@Getter
@AllArgsConstructor
public enum BybitSide {
  BUY("Buy"),

  SELL("Sell");

  @JsonValue private final String value;
}
