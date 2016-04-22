package org.knowm.xchange.bitfinex.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class BitfinexException extends RuntimeException {

  @JsonProperty("message")
  private String message;

  public BitfinexException(@JsonProperty("message") String message) {

    super();
    this.message = message;
  }

  public String getMessage() {

    return message;
  }

}