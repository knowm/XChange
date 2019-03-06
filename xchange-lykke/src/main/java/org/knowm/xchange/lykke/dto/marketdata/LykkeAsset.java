package org.knowm.xchange.lykke.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeAsset {

  private String id;
  private String name;
  private String displayId;
  private String bitcoinAssetId;
  private String bitcoinAssetAddress;
  private String symbol;
  private int accuracy;

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

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDisplayId() {
    return displayId;
  }

  public void setDisplayId(String displayId) {
    this.displayId = displayId;
  }

  public String getBitcoinAssetId() {
    return bitcoinAssetId;
  }

  public void setBitcoinAssetId(String bitcoinAssetId) {
    this.bitcoinAssetId = bitcoinAssetId;
  }

  public String getBitcoinAssetAddress() {
    return bitcoinAssetAddress;
  }

  public void setBitcoinAssetAddress(String bitcoinAssetAddress) {
    this.bitcoinAssetAddress = bitcoinAssetAddress;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public int getAccuracy() {
    return accuracy;
  }

  public void setAccuracy(int accuracy) {
    this.accuracy = accuracy;
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
