package org.knowm.xchange.lykke.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeTradeHistory {

  @JsonProperty("Id")
  private final String id;

  @JsonProperty("DateTime")
  private final String datetime;

  @JsonProperty("State")
  private final LykkeTradeState state;

  @JsonProperty("Amount")
  private final double amount;

  @JsonProperty("Asset")
  private final String asset;

  @JsonProperty("AssetPair")
  private final String assetPair;

  @JsonProperty("Price")
  private final double price;

  @JsonProperty("Fee")
  private final LykkeFee fee;

  public LykkeTradeHistory(
      @JsonProperty("Id") String id,
      @JsonProperty("DateTime") String datetime,
      @JsonProperty("State") LykkeTradeState state,
      @JsonProperty("Amount") double amount,
      @JsonProperty("Asset") String asset,
      @JsonProperty("AssetPair") String assetPair,
      @JsonProperty("Price") double price,
      @JsonProperty("Fee") LykkeFee fee) {
    this.id = id;
    this.datetime = datetime;
    this.state = state;
    this.amount = amount;
    this.asset = asset;
    this.assetPair = assetPair;
    this.price = price;
    this.fee = fee;
  }

  public String getId() {
    return id;
  }

  public String getDatetime() {
    return datetime;
  }

  public LykkeTradeState getState() {
    return state;
  }

  public double getAmount() {
    return amount;
  }

  public String getAsset() {
    return asset;
  }

  public String getAssetPair() {
    return assetPair;
  }

  public double getPrice() {
    return price;
  }

  public LykkeFee getFee() {
    return fee;
  }

  @Override
  public String toString() {
    return "TradeHistory{"
        + "id='"
        + id
        + '\''
        + ", datetime='"
        + datetime
        + '\''
        + ", state="
        + state
        + ", amount="
        + amount
        + ", asset='"
        + asset
        + '\''
        + ", assetPair='"
        + assetPair
        + '\''
        + ", price="
        + price
        + ", fee="
        + fee
        + '}';
  }
}
