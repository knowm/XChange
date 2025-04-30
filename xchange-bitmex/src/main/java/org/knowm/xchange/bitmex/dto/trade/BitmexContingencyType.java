package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BitmexContingencyType {
  @JsonProperty("OneCancelsTheOther")
  OCO,
  @JsonProperty("OneTriggersTheOther")
  OTO;
}
