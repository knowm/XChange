package org.knowm.xchange.lykke.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class LykkeLimitOrder {

  @JsonProperty("AssetPairId")
  private final String assetPairId;

  @JsonProperty("OrderAction")
  private final LykkeOrderType orderAction;

  @JsonPropertyOrder("Volume")
  private final double volume;

  @JsonPropertyOrder("Price")
  private final double price;

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

  public LykkeOrderType getOrderAction() {
    return orderAction;
  }

  public double getVolume() {
    return volume;
  }

  public double getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "LimitOrder{"
        + "assetPairId='"
        + assetPairId
        + '\''
        + ", orderAction="
        + orderAction
        + ", volume="
        + volume
        + ", price="
        + price
        + '}';
  }
}
