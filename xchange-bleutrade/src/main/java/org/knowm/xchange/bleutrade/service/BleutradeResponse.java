package org.knowm.xchange.bleutrade.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BleutradeResponse<T> {
  public final boolean success;
  public final String message;
  public final T result;

  public BleutradeResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message, @JsonProperty("result") T result) {
    this.success = success;
    this.message = message;
    this.result = result;
  }
}
