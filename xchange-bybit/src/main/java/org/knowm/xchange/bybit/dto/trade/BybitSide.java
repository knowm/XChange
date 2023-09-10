package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BybitSide {
  @JsonProperty("Buy")
  BUY,

  @JsonProperty("Sell")
  SELL
}
