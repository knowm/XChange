package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BitmexTimeInForce {
  @JsonProperty("Day")
  DAY,
  @JsonProperty("GoodTillCancel")
  GOOD_TILL_CANCEL,
  @JsonProperty("ImmediateOrCancel")
  IMMEDIATE_OR_CANCEL,
  @JsonProperty("FillOrKill")
  FILL_OR_KILL;
}
