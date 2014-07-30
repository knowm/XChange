package com.xeiam.xchange.lakebtc.dto;

/**
 * @author kpysniak
 */
public abstract class LakeBTCBaseResponse {

  private final String error;

  protected LakeBTCBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
