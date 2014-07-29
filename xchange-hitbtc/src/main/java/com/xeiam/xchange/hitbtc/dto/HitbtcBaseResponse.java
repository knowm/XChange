package com.xeiam.xchange.hitbtc.dto;

/**
 * @author kpysniak
 */
public abstract class HitbtcBaseResponse {

  private final String error;

  protected HitbtcBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
