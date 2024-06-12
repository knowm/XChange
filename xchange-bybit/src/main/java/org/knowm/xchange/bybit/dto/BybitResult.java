package org.knowm.xchange.bybit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitResult<V> {

  @JsonProperty("retCode")
  int retCode;

  @JsonProperty("retMsg")
  String retMsg;

  @JsonProperty("result")
  V result;

  @JsonProperty("retExtInfo")
  Object retExtInfo;

  @JsonProperty("time")
  Date time;

  public boolean isSuccess() {
    return retCode == 0;
  }
}
