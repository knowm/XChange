package org.knowm.xchange.cobinhood.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CobinhoodResponse<T> {
  private final Boolean success;
  private final CobinhoodError error;
  private final T result;

  public CobinhoodResponse(
      @JsonProperty("success") Boolean success,
      @JsonProperty("error") CobinhoodError error,
      @JsonProperty("result") T result) {
    this.success = success;
    this.error = error;
    this.result = result;
  }

  public Boolean isSuccess() {
    return success;
  }

  public CobinhoodError getError() {
    return error;
  }

  public T getResult() {
    return result;
  }
}
