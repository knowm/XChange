package org.knowm.xchange.huobi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class HuobiDepositAddressWithTag {

  private final String address;
  private final String tag;

  public HuobiDepositAddressWithTag(
      @JsonProperty("address") String address, @JsonProperty("tag") String tag) {
    this.address = address;
    this.tag = tag;
  }

  public String getAddress() {
    return address;
  }

  public String getTag() {
    return tag;
  }

  @Override
  public String toString() {
    return "HuobiDepositAddressWithTag [address=" + getAddress() + ", tag=" + getTag() + "]";
  }
}
