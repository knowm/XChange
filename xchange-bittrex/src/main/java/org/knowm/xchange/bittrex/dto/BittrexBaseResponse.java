package org.knowm.xchange.bittrex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.ExceptionalReturnContentException;

public class BittrexBaseResponse<T> {

  private final T result;

  public BittrexBaseResponse(
      @JsonProperty("success") Boolean success,
      @JsonProperty("message") String message,
      @JsonProperty("result") T result) {

    if (!success) {
      throw new ExceptionalReturnContentException(message);
    }

    this.result = result;
  }

  public T getResult() {
    return result;
  }
}
