package org.knowm.xchange.bithumb.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BithumbWalletAddress {

  private final String currency;
  private final String walletAddress;

  public BithumbWalletAddress(
      @JsonProperty("currency") String currency,
      @JsonProperty("wallet_address") String walletAddress) {
    this.currency = currency;
    this.walletAddress = walletAddress;
  }

  public String getCurrency() {
    return currency;
  }

  public String getWalletAddress() {
    return walletAddress;
  }

  @Override
  public String toString() {
    return "BithumbWalletAddress{"
        + "currency='"
        + currency
        + '\''
        + ", walletAddress='"
        + walletAddress
        + '\''
        + '}';
  }
}
