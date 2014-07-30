package com.xeiam.xchange.vaultofsatoshi.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing list of trades from VaultOfSatoshi
 */

public final class TradesWrapper {

  private final String status;
  private final List<VaultOfSatoshiTrade> data;

  @JsonCreator
  public TradesWrapper(@JsonProperty("data") List<VaultOfSatoshiTrade> data, @JsonProperty("status") String status) {

    this.status = status;
    this.data = data;
  }

  public String getStatus() {

    return status;
  }

  public List<VaultOfSatoshiTrade> getTrades() {

    return data;
  }

}
