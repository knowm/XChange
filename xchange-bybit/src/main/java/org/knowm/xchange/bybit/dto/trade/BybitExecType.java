package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BybitExecType {
  TRADE("Trade"),

  ADL_TRADE("AdlTrade"),

  FUNDING("Funding"),

  BUST_TRADE("BustTrade"),

  DELIVERY("Delivery"),

  BLOCK_TRADE("BlockTrade");
  @JsonValue
  private final String value;
}
