package org.knowm.xchange.coincheck.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import si.mazi.rescu.HttpStatusExceptionSupport;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class CoincheckException extends HttpStatusExceptionSupport {
  @JsonProperty private final boolean success;
  @JsonProperty private final String error;

  @JsonCreator
  public CoincheckException(
      @JsonProperty("success") boolean success, @JsonProperty("error") String error) {
    super(error);
    this.success = success;
    this.error = error;
  }
}
