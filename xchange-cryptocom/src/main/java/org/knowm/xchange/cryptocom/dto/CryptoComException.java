package org.knowm.xchange.cryptocom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import si.mazi.rescu.HttpStatusExceptionSupport;

@Getter
public class CryptoComException extends HttpStatusExceptionSupport {

  private final int code;

  public CryptoComException(@JsonProperty("code") int code, @JsonProperty("message") String message) {
    super(message);
    this.code = code;
  }

  @Override
  public String getMessage() {
    return String.format("[%d] %s", code, super.getMessage());
  }
}
