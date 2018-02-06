package org.knowm.xchange.hitbtc.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcAddress {

  private final String address;
  private final String paymentId;

  public HitbtcAddress(@JsonProperty("address") String address, @JsonProperty("paymentId") String paymentId) {
    this.address = address;
    this.paymentId = paymentId;
  }

  public String getAddress() {
    return address;
  }

  public String getPaymentId() {
    return paymentId;
  }

  @Override
  public String toString() {
    return "HitbtcAddress{" + "address='" + address + '\'' + "paymentId='" + paymentId + '\'' + '}';
  }
}
