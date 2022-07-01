package org.knowm.xchange.coinjar.dto.trading;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinjarAccount {
  public final String number;
  public final String type;
  public final String assetCode;
  public final String balance;
  public final String settledBalance;
  public final String hold;
  public final String available;

  public CoinjarAccount(
      @JsonProperty("number") String number,
      @JsonProperty("type") String type,
      @JsonProperty("asset_code") String assetCode,
      @JsonProperty("balance") String balance,
      @JsonProperty("settled_balance") String settledBalance,
      @JsonProperty("hold") String hold,
      @JsonProperty("available") String available) {
    this.number = number;
    this.type = type;
    this.assetCode = assetCode;
    this.balance = balance;
    this.settledBalance = settledBalance;
    this.hold = hold;
    this.available = available;
  }
}
