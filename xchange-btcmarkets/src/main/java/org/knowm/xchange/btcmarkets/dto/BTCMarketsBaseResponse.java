package org.knowm.xchange.btcmarkets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import si.mazi.rescu.ExceptionalReturnContentException;

public class BTCMarketsBaseResponse {

  private final Boolean success;
  private final String errorMessage;
  private final Integer errorCode;

  protected BTCMarketsBaseResponse(
      @JsonProperty("success") Boolean success,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("errorCode") Integer errorCode) {
    if (!Objects.equals(success, Boolean.TRUE)) {
      throw new ExceptionalReturnContentException(errorMessage);
    }
    this.success = success;
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
  }

  @Override
  public String toString() {
    return String.format(
        "%s{success=%s, errorMessage='%s', errorCode=%d}",
        getClass().getSimpleName(), success, errorMessage, errorCode);
  }

  public Boolean getSuccess() {
    return success;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public Integer getErrorCode() {
    return errorCode;
  }
}
