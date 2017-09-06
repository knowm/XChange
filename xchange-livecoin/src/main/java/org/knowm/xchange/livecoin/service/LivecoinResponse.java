package org.knowm.xchange.livecoin.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LivecoinResponse<T> {
  public final boolean success;
  public final Integer errorCode;
  public final String errorMessage;
  public final T data;

  public LivecoinResponse(@JsonProperty("success") boolean success, @JsonProperty("errorCode") Integer errorCode, @JsonProperty("errorMessage") String errorMessage, @JsonProperty("response") T data) {
    this.success = success;
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.data = data;
  }
}
