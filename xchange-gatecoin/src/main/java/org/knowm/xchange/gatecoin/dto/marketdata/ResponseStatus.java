package org.knowm.xchange.gatecoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author sumedha */
public class ResponseStatus {

  private final String message;
  private final String errorCode;

  @JsonCreator
  public ResponseStatus(
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

  @Override
  public String toString() {
    return String.format("ResponseStatus{message='%s', errorCode='%s'}", message, errorCode);
  }
}
