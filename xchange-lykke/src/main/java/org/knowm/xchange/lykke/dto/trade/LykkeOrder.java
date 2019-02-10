package org.knowm.xchange.lykke.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeOrder {

    private String id;
    private String status;
    private String assetPairId;
    private double volume;
    private double price;
    private double remainingVolume;
    private String lastMatchTime;
    private String createdAt;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssetPairId() {
        return assetPairId;
    }

    public void setAssetPairId(String assetPairId) {
        this.assetPairId = assetPairId;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRemainingVolume() {
        return remainingVolume;
    }

    public void setRemainingVolume(double remainingVolume) {
        this.remainingVolume = remainingVolume;
    }

    public String getLastMatchTime() {
        return lastMatchTime;
    }

    public void setLastMatchTime(String lastMatchTime) {
        this.lastMatchTime = lastMatchTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", assetPairId='" + assetPairId + '\'' +
                ", volume=" + volume +
                ", price=" + price +
                ", remainingVolume=" + remainingVolume +
                ", lastMatchTime='" + lastMatchTime + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
