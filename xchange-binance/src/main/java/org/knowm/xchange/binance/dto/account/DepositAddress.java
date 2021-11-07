package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class DepositAddress {

  public String address;
  public String url;
  public String addressTag;
  public String asset;

  public DepositAddress(
      @JsonProperty("address") String address,
      @JsonProperty("url") String url,
      @JsonProperty("tag") String addressTag,
      @JsonProperty("coin") String asset) {
    this.address = address;
    this.url = url;
    this.addressTag = addressTag;
    this.asset = asset;
  }
}
