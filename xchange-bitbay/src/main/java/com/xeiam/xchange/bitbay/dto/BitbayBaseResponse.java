package com.xeiam.xchange.bitbay.dto;

/**
 * @author kpysniak
 */
public abstract class BitbayBaseResponse {

  private final String error;

  protected BitbayBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
