package org.knowm.xchange.bybit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitResult<V> {

  @JsonProperty("ret_code")
  int retCode;

  @JsonProperty("ret_msg")
  String retMsg;

  @JsonProperty("ext_code")
  String extCode;

  @JsonProperty("ext_info")
  String extInfo;

  @JsonProperty("result")
  V result;

  public boolean isSuccess() {
    return retCode == 0;
  }

}
