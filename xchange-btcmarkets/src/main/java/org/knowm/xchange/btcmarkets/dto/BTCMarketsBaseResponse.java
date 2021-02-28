package org.knowm.xchange.btcmarkets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import si.mazi.rescu.ExceptionalReturnContentException;

public class BTCMarketsBaseResponse {

  public final Boolean success;
  public final String errorMessage;
  public final Integer errorCode;

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
}
