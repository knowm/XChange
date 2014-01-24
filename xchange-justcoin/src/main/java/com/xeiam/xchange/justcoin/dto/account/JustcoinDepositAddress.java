package com.xeiam.xchange.justcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class JustcoinDepositAddress {

  private final String address;

  public JustcoinDepositAddress(final @JsonProperty("address") String address) {

    this.address = address;
  }

  public String getAddress() {

    return address;
  }
}
