package org.knowm.xchange.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class KucoinException extends RuntimeException {

  @JsonProperty("code")
  int code;

  @JsonProperty("msg")
  String message;

}
