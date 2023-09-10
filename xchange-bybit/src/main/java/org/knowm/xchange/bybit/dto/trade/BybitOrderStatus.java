package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BybitOrderStatus {
  @JsonProperty("Created")
  CREATED,

  @JsonProperty("New")
  NEW,

  @JsonProperty("Rejected")
  REJECTED,

  @JsonProperty("PartiallyFilled")
  PARTIALLY_FILLED,

  @JsonProperty("PartiallyFilledCanceled")
  PARTIALLY_FILLED_CANCELED,

  @JsonProperty("Filled")
  FILLED,

  @JsonProperty("Cancelled")
  CANCELLED,

  @JsonProperty("Untriggered")
  UNTRIGGERED,

  @JsonProperty("Triggered")
  TRIGGERED,

  @JsonProperty("Deactivated")
  DEACTIVATED,

  @JsonProperty("Active")
  ACTIVE
}
