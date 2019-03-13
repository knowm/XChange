package org.knowm.xchange.lykke.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeOrder {

  @JsonProperty("Id")
  private final String id;

  @JsonProperty("Status")
  private final String status;

  @JsonProperty("AssetPairId")
  private final String assetPairId;

  @JsonProperty("Volume")
  private final double volume;

  @JsonProperty("Price")
  private final double price;

  @JsonProperty("RemainingVolume")
  private final double remainingVolume;

  @JsonProperty("LastMatchTime")
  private final String lastMatchTime;

  @JsonProperty("CreatedAt")
  private final String createdAt;

  public LykkeOrder(
      @JsonProperty("Id") String id,
      @JsonProperty("Status") String status,
      @JsonProperty("AssetPairId") String assetPairId,
      @JsonProperty("Volume") double volume,
      @JsonProperty("Price") double price,
      @JsonProperty("RemainingVolume") double remainingVolume,
      @JsonProperty("LastMatchTime") String lastMatchTime,
      @JsonProperty("CreatedAt") String createdAt) {
    this.id = id;
    this.status = status;
    this.assetPairId = assetPairId;
    this.volume = volume;
    this.price = price;
    this.remainingVolume = remainingVolume;
    this.lastMatchTime = lastMatchTime;
    this.createdAt = createdAt;
  }

  public String getId() {
    return id;
  }

  public String getStatus() {
    return status;
  }

  public String getAssetPairId() {
    return assetPairId;
  }

  public double getVolume() {
    return volume;
  }

  public double getPrice() {
    return price;
  }

  public double getRemainingVolume() {
    return remainingVolume;
  }

  public String getLastMatchTime() {
    return lastMatchTime;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  @Override
  public String toString() {
    return "Order{"
        + "id='"
        + id
        + '\''
        + ", status='"
        + status
        + '\''
        + ", assetPairId='"
        + assetPairId
        + '\''
        + ", volume="
        + volume
        + ", price="
        + price
        + ", remainingVolume="
        + remainingVolume
        + ", lastMatchTime='"
        + lastMatchTime
        + '\''
        + ", createdAt='"
        + createdAt
        + '\''
        + '}';
  }
}
