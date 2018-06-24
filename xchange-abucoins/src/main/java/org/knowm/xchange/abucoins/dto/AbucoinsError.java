package org.knowm.xchange.abucoins.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author walec51 */
public class AbucoinsError {

  private final String message;

  public AbucoinsError(@JsonProperty("message") String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
