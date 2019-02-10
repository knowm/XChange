package org.knowm.xchange.lykke.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeTradeHistory {
    private String id;
    private String datetime;
    private LykkeTradeState state;
    private double amount;
    private String asset;
    private String assetPair;
    private double price;
    private LykkeFee fee;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public LykkeTradeState getState() {
        return state;
    }

    public void setState(LykkeTradeState state) {
        this.state = state;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getAssetPair() {
        return assetPair;
    }

    public void setAssetPair(String assetPair) {
        this.assetPair = assetPair;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LykkeFee getFee() {
        return fee;
    }

    public void setFee(LykkeFee fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "TradeHistory{" +
                "id='" + id + '\'' +
                ", datetime='" + datetime + '\'' +
                ", state=" + state +
                ", amount=" + amount +
                ", asset='" + asset + '\'' +
                ", assetPair='" + assetPair + '\'' +
                ", price=" + price +
                ", fee=" + fee +
                '}';
    }
}
