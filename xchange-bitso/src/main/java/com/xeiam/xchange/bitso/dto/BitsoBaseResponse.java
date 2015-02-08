package com.xeiam.xchange.bitso.dto;

public abstract class BitsoBaseResponse {

  private final String error;

  protected BitsoBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
