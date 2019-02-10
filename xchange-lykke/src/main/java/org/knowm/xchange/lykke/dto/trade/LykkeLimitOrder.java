package org.knowm.xchange.lykke.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeLimitOrder {

    private String assetPairId;
    private LykkeOrderType orderAction;
    private double volume;
    private double price;

    public LykkeLimitOrder(
            @JsonProperty("AssetPairId") String assetPairId,
            @JsonProperty("OrderAction") LykkeOrderType orderAction,
            @JsonProperty("Volume") double volume,
            @JsonProperty("Price") double price) {
        this.assetPairId = assetPairId;
        this.orderAction = orderAction;
        this.volume = volume;
        this.price = price;
    }

    public String getAssetPairId() {
        return assetPairId;
    }

    public void setAssetPairId(String assetPairId) {
        this.assetPairId = assetPairId;
    }

    public LykkeOrderType getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(LykkeOrderType orderAction) {
        this.orderAction = orderAction;
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

    @Override
    public String toString() {
        return "LimitOrder{" +
                "assetPairId='" + assetPairId + '\'' +
                ", orderAction=" + orderAction +
                ", volume=" + volume +
                ", price=" + price +
                '}';
    }
}
