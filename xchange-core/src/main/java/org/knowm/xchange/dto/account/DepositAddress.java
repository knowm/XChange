package org.knowm.xchange.dto.account;

import java.io.Serializable;

public class DepositAddress implements Serializable {

  private final String currency;
  private final String address;
  private final String addressTag;
  private final String chain;

  public DepositAddress(String currency, String address, String addressTag, String chain) {
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
}
