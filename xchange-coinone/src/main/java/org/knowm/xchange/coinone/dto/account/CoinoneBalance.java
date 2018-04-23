package org.knowm.xchange.coinone.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinoneBalance {

  private final double avail;
  private final double balance;

  public CoinoneBalance(
      @JsonProperty("avail") String avail, @JsonProperty("balance") String balance) {
    this.avail = Double.valueOf(avail);
    this.balance = Double.valueOf(balance);
  }

  public double getAvail() {
    return avail;
  }

  public double getBalance() {
    return balance;
  }
}
