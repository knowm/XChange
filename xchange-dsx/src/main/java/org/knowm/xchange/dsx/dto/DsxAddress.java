package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DsxAddress {

  private final String address;
  private final String paymentId;

  public DsxAddress(
      @JsonProperty("address") String address, @JsonProperty("paymentId") String paymentId) {
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
    return "DsxAddress{" + "address='" + address + '\'' + "paymentId='" + paymentId + '\'' + '}';
  }
}
