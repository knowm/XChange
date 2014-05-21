package com.xeiam.xchange.bitmarket.dto;

/**
 * @author kpysniak
 */
public abstract class BitMarketBaseResponse {

  private final String error;

  protected BitMarketBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
