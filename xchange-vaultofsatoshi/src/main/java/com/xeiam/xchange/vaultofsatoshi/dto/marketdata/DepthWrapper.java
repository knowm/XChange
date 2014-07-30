package com.xeiam.xchange.vaultofsatoshi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing depth from VaultOfSatoshi
 */

public final class DepthWrapper {

  private final String status;
  private final VaultOfSatoshiDepth data;

  @JsonCreator
  public DepthWrapper(@JsonProperty("data") VaultOfSatoshiDepth data, @JsonProperty("status") String status) {

    this.status = status;
    this.data = data;
  }

  public String getStatus() {

    return status;
  }

  public VaultOfSatoshiDepth getDepth() {

    return data;
  }

}
