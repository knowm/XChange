package org.knowm.xchange.bitfinex.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexException extends RuntimeException {

  @JsonProperty("message")
  private String message;

  @JsonProperty("error")
  private String error;

  public BitfinexException(@JsonProperty("message") String message) {

    super();
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message == null ? error : message;
  }

  public String getError() {
    return error;
  }
}
