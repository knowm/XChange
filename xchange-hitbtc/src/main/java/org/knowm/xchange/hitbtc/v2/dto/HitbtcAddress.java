package org.knowm.xchange.hitbtc.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcAddress {

  private final String address;

  public HitbtcAddress(@JsonProperty("address") String address) {
    this.address = address;
  }

  public String getAddress() {
    return address;
  }

  @Override
  public String toString() {
    return "HitbtcAddress{" +
        "address='" + address + '\'' +
        '}';
  }
}
