package org.knowm.xchange.clevercoin.dto;

public class CleverCoinBaseResponse {

  private final String error;

  protected CleverCoinBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
