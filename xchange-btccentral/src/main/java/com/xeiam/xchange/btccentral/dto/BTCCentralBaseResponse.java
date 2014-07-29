package com.xeiam.xchange.btccentral.dto;

/**
 * @author kpysniak
 */
public abstract class BTCCentralBaseResponse {

  private final String error;

  protected BTCCentralBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
