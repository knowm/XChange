package org.knowm.xchange.huobi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiDepositAddress {

  private final String currency;
  private final String address;
  private final String addressTag;
  private final String chain;

  public HuobiDepositAddress(
      @JsonProperty("currency") String currency,
      @JsonProperty("address") String address,
      @JsonProperty("addressTag") String addressTag,
      @JsonProperty("chain") String chain) {
    this.currency = currency;
    this.address = address;
    this.addressTag = addressTag;
    this.chain = chain;
  }

  public String getCurrency() {
    return currency;
  }

  public String getAddress() {
    return address;
  }

  public String getAddressTag() {
    return addressTag;
  }

  public String getChain() {
    return chain;
  }

  @Override
  public String toString() {
    return "HuobiDepositAddress [" +
            "currency='" + currency + '\'' +
            ", address='" + address + '\'' +
            ", addressTag='" + addressTag + '\'' +
            ", chain='" + chain + '\'' +
            ']';
  }

}
