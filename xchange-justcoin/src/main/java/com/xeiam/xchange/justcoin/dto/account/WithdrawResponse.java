package com.xeiam.xchange.justcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class WithdrawResponse {

  private final int id;

  public WithdrawResponse(final @JsonProperty("id") int id) {

    this.id = id;
  }

  public int getId() {

    return id;
  }
}
