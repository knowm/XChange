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

  /**
   * Constructor
   */
  public CryptoTradeException() {

  }

  public String getMessage() {

    return message;
  }

  public String getStatus() {

    return status;
  }

  @Override
  public String toString() {

    return String.format("CryptoTradeException [status='%s', message='%s']", status, message);
  }
}
