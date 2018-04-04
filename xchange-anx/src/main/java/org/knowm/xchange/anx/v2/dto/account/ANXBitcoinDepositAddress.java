package org.knowm.xchange.anx.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing the content of a response message from ANX after requesting a bitcoin
 * deposit address
 */
public final class ANXBitcoinDepositAddress {

  private final String address;

  /**
   * Constructor
   *
   * @param address The Bitcoin deposit address
   */
  @JsonCreator
  public ANXBitcoinDepositAddress(@JsonProperty("addr") String address) {

    this.address = address;
  }

  public String getAddress() {

    return address;
  }

  @Override
  public String toString() {

    return "ANXBitcoinDepositAddress [address=" + address + "]";
  }
}
