package org.knowm.xchange.gatecoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author sumedha */
public class Response {

  private final String message;
  private final String errorCode;

  @JsonCreator
  public Response(
      @JsonProperty("message") String message, @JsonProperty("errorCode") String errorCode) {
    this.message = message;
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getMessage() {
    return this.message;
  }
}
