package org.knowm.xchange.cryptonit2.dto;

public class CryptonitBaseResponse {

  private final String error;

  protected CryptonitBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
