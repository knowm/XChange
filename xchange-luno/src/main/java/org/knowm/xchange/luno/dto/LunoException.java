package org.knowm.xchange.luno.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoException extends RuntimeException {

  private final String error;
  private final String errorCode;

  public LunoException(
      @JsonProperty(value = "error", required = true) String error,
      @JsonProperty("error_code") String errorCode) {
    super(errorCode + ": " + error);
    this.error = error;
    this.errorCode = errorCode;
  }

  public String getError() {
    return error;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
