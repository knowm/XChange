package com.xeiam.xchange.cointrader.dto;

import si.mazi.rescu.HttpStatusExceptionSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CointraderException extends HttpStatusExceptionSupport {

  private final ErrorData errorData;

  public CointraderException(@JsonProperty("message") String message, @JsonProperty("data") ErrorData errorData) {
    super(errorData.message);
    this.errorData = errorData;
  }

  public ErrorData getErrorData() {
    return errorData;
  }

  public static class ErrorData {
    public final Integer errorCode;
    public final String message;

    public ErrorData(@JsonProperty("errorCode") Integer errorCode, @JsonProperty("message") String message) {
      this.errorCode = errorCode;
      this.message = message;
    }
  }
}
