package org.knowm.xchange.lykke.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeWallet {
  @JsonProperty("AssetId")
  private final String assetId;

  @JsonProperty("Balance")
  private final double balance;

  @JsonProperty("Reserved")
  private final double reserved;

  public LykkeWallet(
      @JsonProperty("AssetId") String assetId,
      @JsonProperty("Balance") double balance,
      @JsonProperty("Reserved") double reserved) {
    this.assetId = assetId;
    this.balance = balance;
    this.reserved = reserved;
  }

  public String getAssetId() {
    return assetId;
  }

  public double getBalance() {
    return balance;
  }

  public double getReserved() {
    return reserved;
  }

  @Override
  public String toString() {
    return "Wallet{"
        + "assetId='"
        + assetId
        + '\''
        + ", balance="
        + balance
        + ", reserved="
        + reserved
        + '}';
  }
}
