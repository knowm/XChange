package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BitmexExecutionInstruction {
  @JsonProperty("ParticipateDoNotInitiate")
  PARTICIPATE_DO_NOT_INITIATE,
  @JsonProperty("AllOrNone")
  ALL_OR_NONE,
  @JsonProperty("MarkPrice")
  MARK_PRICE,
  @JsonProperty("IndexPrice")
  INDEX_PRICE,
  @JsonProperty("LastPrice")
  LAST_PRICE,
  @JsonProperty("Close")
  CLOSE,
  @JsonProperty("ReduceOnly")
  REDUCE_ONLY,
  @JsonProperty("Fixed")
  FIXED,
  @JsonProperty("LastWithinMark")
  LAST_WITHIN_MARK;
}
