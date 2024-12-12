package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BitmexPegPriceType {
  @JsonProperty("MarketPeg")
  MARGET_PEG,
  @JsonProperty("PrimaryPeg")
  PRIMARY_PEG,
  @JsonProperty("TrailingStopPeg")
  TRAILING_STOP_PEG;
}
