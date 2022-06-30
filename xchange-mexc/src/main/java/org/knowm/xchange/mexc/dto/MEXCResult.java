package org.knowm.xchange.mexc.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MEXCResult<T> {

  private final int code;
  private final T data;

  @JsonCreator
  public MEXCResult(
          @JsonProperty("code") int code,
          @JsonProperty("data") T data) {
    this.code = code;
    this.data = data;
  }

  public T getData() {
    return data;
  }

}
