package org.knowm.xchange.bybit.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.Map;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class BybitException extends RuntimeException {

  @JsonProperty("retCode")
  int retCode;

  @JsonProperty("retMsg")
  String retMsg;

  @JsonProperty("retExtInfo")
  Map extInfo;

  @JsonProperty("result")
  Map result;

  @JsonProperty("time")
  Instant timestamp;



}
