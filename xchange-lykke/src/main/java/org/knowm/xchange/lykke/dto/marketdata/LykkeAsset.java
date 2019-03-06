package org.knowm.xchange.lykke.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeAsset {
  @JsonProperty("id")
  private final String id;

  @JsonProperty("name")
  private final String name;

  @JsonProperty("displayId")
  private final String displayId;

  @JsonProperty("bitcoinAssetId")
  private final String bitcoinAssetId;

  @JsonProperty("bitcoinAssetAddress")
  private final String bitcoinAssetAddress;

  @JsonProperty("symbol")
  private final String symbol;

  @JsonProperty("accuracy")
  private final int accuracy;

  public LykkeAsset(
      @JsonProperty("id") String id,
      @JsonProperty("name") String name,
      @JsonProperty("displayId") String displayId,
      @JsonProperty("bitcoinAssetId") String bitcoinAssetId,
      @JsonProperty("bitcoinAssetAddress") String bitcoinAssetAddress,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("accuracy") int accuracy) {
    this.id = id;
    this.name = name;
    this.displayId = displayId;
    this.bitcoinAssetId = bitcoinAssetId;
    this.bitcoinAssetAddress = bitcoinAssetAddress;
    this.symbol = symbol;
    this.accuracy = accuracy;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDisplayId() {
    return displayId;
  }

  public String getBitcoinAssetId() {
    return bitcoinAssetId;
  }

  public String getBitcoinAssetAddress() {
    return bitcoinAssetAddress;
  }

  public String getSymbol() {
    return symbol;
  }

  public int getAccuracy() {
    return accuracy;
  }

  @Override
  public String toString() {
    return "LykkeAsset{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", displayId='"
        + displayId
        + '\''
        + ", bitcoinAssetId='"
        + bitcoinAssetId
        + '\''
        + ", bitcoinAssetAddress='"
        + bitcoinAssetAddress
        + '\''
        + ", symbol='"
        + symbol
        + '\''
        + ", accuracy="
        + accuracy
        + '}';
  }
}
