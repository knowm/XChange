package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class DepositAddress {

  public String address;
  public boolean success;
  public String addressTag;
  public String asset;

  public DepositAddress(
      @JsonProperty("address") String address,
      @JsonProperty("success") boolean success,
      @JsonProperty("addressTag") String addressTag,
      @JsonProperty("asset") String asset) {
    this.address = address;
    this.success = success;
    this.addressTag = addressTag;
    this.asset = asset;
  }
}
