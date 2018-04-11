package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenDepositAddress {

  protected final String address;
  protected final String tag;
  private final Long expiretm;
  private final boolean newAddress;

  public KrakenDepositAddress(
      @JsonProperty("address") String address,
      @JsonProperty("tag") String tag,
      @JsonProperty("expiretm") Long expiretm,
      @JsonProperty("new") boolean newAddress) {
    super();
    this.address = address;
    this.tag = tag;
    this.expiretm = expiretm;
    this.newAddress = newAddress;
  }

  public String getAddress() {
    return address;
  }

  public String getTag() {
    return tag;
  }

  public Long getExpiretm() {
    return expiretm;
  }

  public boolean isNewAddress() {
    return newAddress;
  }

  @Override
  public String toString() {
    return "KrakenDepositAddress [address="
        + address
        + ", tag="
        + tag
        + ", expiretm="
        + expiretm
        + ", newAddress="
        + newAddress
        + "]";
  }
}
