package org.knowm.xchange.coinsph.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoinsphResponse {
  private int code;

  @JsonProperty("msg")
  private String message;
}
