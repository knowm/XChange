package org.knowm.xchange.dsx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXCryptoDepositAddress {

  private final String address;
  private final int isNew;
  private final String currency;

  public DSXCryptoDepositAddress(@JsonProperty("address") String address, @JsonProperty("new") int isNew, @JsonProperty("currency") String currency) {
    this.address = address;
    this.isNew = isNew;
    this.currency = currency;
  }

  public String getAddress() {
    return address;
  }

  public int getIsNew() {
    return isNew;
  }

  public String getCurrency() {
    return currency;
  }

  @Override
  public String toString() {
    return "DSXCryptoDepositAddress{" +
        "cryptoAddress='" + address + '\'' +
        ", newAddress=" + isNew +
        ", currency='" + currency + '\'' +
        '}';
  }
}
