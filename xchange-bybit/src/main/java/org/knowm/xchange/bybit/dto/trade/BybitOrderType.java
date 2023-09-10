package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BybitOrderType {
  @JsonProperty("Market")
  MARKET,

  @JsonProperty("Limit")
  LIMIT
}
