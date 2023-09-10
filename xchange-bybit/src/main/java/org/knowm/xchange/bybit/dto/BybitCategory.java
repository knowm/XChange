package org.knowm.xchange.bybit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BybitCategory {
  @JsonProperty("spot")
  SPOT,
  @JsonProperty("linear")
  LINEAR,
  @JsonProperty("inverse")
  INVERSE,
  @JsonProperty("option")
  OPTION
}
