package com.xeiam.xchange.cryptotrade.dto;

public class CryptoTradeBaseResponse {

  private final String status;
  private final String error;

  protected CryptoTradeBaseResponse(String status, String error) {

    this.status = status;
    this.error = error;
  }

  public String getStatus() {

    return status;
  }

  public String getError() {

    return error;
  }

  @Override
  public String toString() {

    return "CryptoTradeBaseResponse [status=" + status + ", error=" + error + "]";
  }
}
