package org.knowm.xchange.deribit.v2.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Kind {
  @JsonProperty("future")
  FUTURES,

  @JsonProperty("option")
  OPTIONS,

  @JsonProperty("spot")
  SPOT,

  @JsonProperty("future_combo")
  FUTURES_COMBO,

  @JsonProperty("option_combo")
  OPTIONS_COMBO,

  @JsonEnumDefaultValue
  UNKNOWN
}
