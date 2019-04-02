package org.knowm.xchange.coingi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoingiDepositWalletResponse {
  private String address;

  public CoingiDepositWalletResponse(@JsonProperty("address") String address) {
    this.address = address;
  }

  public String getAddress() {
    return address;
  }
}
