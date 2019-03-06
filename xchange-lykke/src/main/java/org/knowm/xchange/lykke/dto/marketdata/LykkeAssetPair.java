package org.knowm.xchange.lykke.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeAssetPair {
  @JsonProperty("Id")
  private final String id;

  @JsonProperty("Name")
  private final String name;

  @JsonProperty("Accuracy")
  private final int accuracy;

  @JsonProperty("InvertedAccuracy")
  private final int invertedAccuracy;

  @JsonProperty("BaseAssetId")
  private final String baseAssetId;

  @JsonProperty("QuotingAssetId")
  private final String quotingAssetId;

  @JsonProperty("MinVolume")
  private final double minVolume;

  @JsonProperty("MinInvertedVolume")
  private final double minInvertedVolume;

  public LykkeAssetPair(
      @JsonProperty("Id") String id,
      @JsonProperty("Name") String name,
      @JsonProperty("Accuracy") int accuracy,
      @JsonProperty("InvertedAccuracy") int invertedAccuracy,
      @JsonProperty("BaseAssetId") String baseAssetId,
      @JsonProperty("QuotingAssetId") String quotingAssetId,
      @JsonProperty("MinVolume") double minVolume,
      @JsonProperty("MinInvertedVolume") double minInvertedVolume) {
    this.id = id;
    this.name = name;
    this.accuracy = accuracy;
    this.invertedAccuracy = invertedAccuracy;
    this.baseAssetId = baseAssetId;
    this.quotingAssetId = quotingAssetId;
    this.minVolume = minVolume;
    this.minInvertedVolume = minInvertedVolume;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAccuracy() {
    return accuracy;
  }

  public int getInvertedAccuracy() {
    return invertedAccuracy;
  }

  public String getBaseAssetId() {
    return baseAssetId;
  }

  public String getQuotingAssetId() {
    return quotingAssetId;
  }

  public double getMinVolume() {
    return minVolume;
  }

  public double getMinInvertedVolume() {
    return minInvertedVolume;
  }

  @Override
  public String toString() {
    return "AssetPair{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", accuracy="
        + accuracy
        + ", invertedAccuracy="
        + invertedAccuracy
        + ", baseAssetId='"
        + baseAssetId
        + '\''
        + ", quotingAssetId='"
        + quotingAssetId
        + '\''
        + ", minVolume="
        + minVolume
        + ", minInvertedVolume="
        + minInvertedVolume
        + '}';
  }
}
