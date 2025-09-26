package org.knowm.xchange.coinsph.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import si.mazi.rescu.HttpStatusExceptionSupport;

@Getter
public class CoinsphException extends HttpStatusExceptionSupport {

  private final int code; // Coins.ph specific error code

  public CoinsphException(@JsonProperty("code") int code, @JsonProperty("message") String message) {
    super(message); // Pass message to parent
    this.code = code;
  }

  @Override
  public String getMessage() {
    return String.format("[%d] %s", code, super.getMessage());
  }
}
