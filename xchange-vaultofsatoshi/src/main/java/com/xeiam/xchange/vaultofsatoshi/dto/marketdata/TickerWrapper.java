package com.xeiam.xchange.vaultofsatoshi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing ticker from VaultOfSatoshi
 */

public final class TickerWrapper {

  private final String status;
  private final VaultOfSatoshiTicker data;

  @JsonCreator
  public TickerWrapper(@JsonProperty("data") VaultOfSatoshiTicker data, @JsonProperty("status") String status) {

    this.status = status;
    this.data = data;
  }

  public String getStatus() {

    return status;
  }

  public VaultOfSatoshiTicker getTicker() {

    return data;
  }

}
