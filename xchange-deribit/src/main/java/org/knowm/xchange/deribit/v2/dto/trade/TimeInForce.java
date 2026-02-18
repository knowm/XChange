package org.knowm.xchange.deribit.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.knowm.xchange.dto.Order.IOrderFlags;

@JsonTypeInfo(use = Id.NONE)
public enum TimeInForce implements IOrderFlags {
  @JsonProperty("good_til_cancelled")
  GOOD_TILL_CANCELLED,

  @JsonProperty("good_til_day")
  GOOD_TILL_DAY,

  @JsonProperty("fill_or_kill")
  FILL_OR_KILL,

  @JsonProperty("immediate_or_cancel")
  IMMEDIATE_OR_CANCEL,

  @JsonEnumDefaultValue
  UNKNOWN
}
