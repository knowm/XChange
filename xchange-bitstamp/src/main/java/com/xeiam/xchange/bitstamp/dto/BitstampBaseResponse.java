package com.xeiam.xchange.bitstamp.dto;

public abstract class BitstampBaseResponse {

  private final String error;

  protected BitstampBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
