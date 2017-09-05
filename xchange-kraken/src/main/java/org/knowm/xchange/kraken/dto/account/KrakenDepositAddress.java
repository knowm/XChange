package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenDepositAddress {

  protected final String address;
  private final Long expiretm;
  private final boolean newAddress;

  public KrakenDepositAddress(@JsonProperty("address") String address, @JsonProperty("expiretm") Long expiretm,
      @JsonProperty("new") boolean newAddress) {
    super();
    this.address = address;
    this.expiretm = expiretm;
    this.newAddress = newAddress;
  }

  public String getAddress() {
    return address;
  }

  public Long getExpiretm() {
    return expiretm;
  }

  public boolean isNewAddress() {
    return newAddress;
  }

  @Override
  public String toString() {
    return "KrakenDepositAddress [address=" + address + ", expiretm=" + expiretm + ", newAddress=" + newAddress + "]";
  }

}
