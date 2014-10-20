package com.xeiam.xchange.cryptotrade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author nimrood
 */
public class CryptoTradeException extends RuntimeException {

  @JsonProperty("status")
  private String status;

  @JsonProperty("message")
  private String message;

  @JsonProperty("error")
  private String error;

  /**
   * Constructor
   */
  public CryptoTradeException() {

  }

  public String getMessage() {

    if (error != null)
      return error;
    return message;
  }

  public String getStatus() {

    return status;
  }

  public String getError() {

    return error;
  }

  @Override
  public String toString() {

    return String.format("CryptoTradeException [status='%s', message='%s', error='%s']", status, message, error);
  }
}
