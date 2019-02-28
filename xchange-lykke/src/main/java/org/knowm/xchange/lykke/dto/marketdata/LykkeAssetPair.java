package org.knowm.xchange.lykke.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class LykkeAssetPair {

    private String id;
    private String name;
    private int accuracy;
    private int invertedAccuracy;
    private String baseAssetId;
    private String quotingAssetId;
    private double minVolume;
    private double minInvertedVolume;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getInvertedAccuracy() {
        return invertedAccuracy;
    }

    public void setInvertedAccuracy(int invertedAccuracy) {
        this.invertedAccuracy = invertedAccuracy;
    }

    public String getBaseAssetId() {
        return baseAssetId;
    }

    public void setBaseAssetId(String baseAssetId) {
        this.baseAssetId = baseAssetId;
    }

    public String getQuotingAssetId() {
        return quotingAssetId;
    }

    public void setQuotingAssetId(String quotingAssetId) {
        this.quotingAssetId = quotingAssetId;
    }

    public double getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(double minVolume) {
        this.minVolume = minVolume;
    }

    public double getMinInvertedVolume() {
        return minInvertedVolume;
    }

    public void setMinInvertedVolume(double minInvertedVolume) {
        this.minInvertedVolume = minInvertedVolume;
    }

    @Override
    public String toString() {
        return "AssetPair{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", accuracy=" + accuracy +
                ", invertedAccuracy=" + invertedAccuracy +
                ", baseAssetId='" + baseAssetId + '\'' +
                ", quotingAssetId='" + quotingAssetId + '\'' +
                ", minVolume=" + minVolume +
                ", minInvertedVolume=" + minInvertedVolume +
                '}';
    }
}
