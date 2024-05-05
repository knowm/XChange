package org.knowm.xchange.gateio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class GateioException extends RuntimeException {

  @JsonProperty("label")
  String label;

  @JsonProperty("message")
  String message;

}
