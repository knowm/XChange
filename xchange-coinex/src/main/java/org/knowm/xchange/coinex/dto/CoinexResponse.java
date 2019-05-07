package org.knowm.xchange.coinex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinexResponse<T> {

  private final int code;
  private final T data;
  private final String message;

  public CoinexResponse(
      @JsonProperty("code") int code,
      @JsonProperty("data") T data,
      @JsonProperty("message") String message) {
    this.code = code;
    this.data = data;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public T getData() {
    return data;
  }

  public String getMessage() {
    return message;
  }
}
