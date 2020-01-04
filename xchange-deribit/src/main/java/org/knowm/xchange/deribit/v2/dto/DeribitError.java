package org.knowm.xchange.deribit.v2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DeribitError {

  @JsonProperty("code")
  private int code;

  @JsonProperty("message")
  private String message;

  @JsonProperty("data")
  private Object data;
}
