package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BitmexOrderType {
  @JsonProperty("Limit")
  LIMIT,
  @JsonProperty("Stop")
  STOP,
  @JsonProperty("Market")
  MARKET,
  @JsonProperty("StopLimit")
  STOP_LIMIT,
  @JsonProperty("Pegged")
  PEGGED,
  @JsonProperty("MarketIfTouched")
  MARKET_IF_TOUCHED,
  @JsonProperty("LimitIfTouched")
  LIMIT_IF_TOUCHED,
  @JsonProperty("MarketWithLeftOverAsLimit")
  MARKET_WITH_LEFT_OVER_AS_LIMIT;
}
