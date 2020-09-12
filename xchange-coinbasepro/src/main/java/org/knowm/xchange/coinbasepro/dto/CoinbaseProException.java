package org.knowm.xchange.coinbasepro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class CoinbaseProException extends HttpStatusExceptionSupport {

  private final String message;

  public CoinbaseProException(@JsonProperty("message") String message) {
    super(message);
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
