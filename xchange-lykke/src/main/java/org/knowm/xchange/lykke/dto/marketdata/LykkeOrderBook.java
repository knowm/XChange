package org.knowm.xchange.lykke.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LykkeOrderBook {

  private String assetPair;
  private boolean isBuy;
  private String timestamp;
  private List<LykkePrices> prices;

  public LykkeOrderBook(
      @JsonProperty("AssetPair") String assetPair,
      @JsonProperty("IsBuy") boolean isBuy,
      @JsonProperty("Timestamp") String timestamp,
      @JsonProperty("Prices") List<LykkePrices> prices) {
    this.assetPair = assetPair;
    this.isBuy = isBuy;
    this.timestamp = timestamp;
    this.prices = prices;
  }

  public String getAssetPair() {
    return assetPair;
  }

  public void setAssetPair(String assetPair) {
    this.assetPair = assetPair;
  }

  public boolean isBuy() {
    return isBuy;
  }

  public void setBuy(boolean buy) {
    isBuy = buy;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public List<LykkePrices> getPrices() {
    return prices;
  }

  public void setPrices(List<LykkePrices> prices) {
    this.prices = prices;
  }

  @Override
  public String toString() {
    return "OrderBook{"
        + "assetPair='"
        + assetPair
        + '\''
        + ", isBuy="
        + isBuy
        + ", timestamp='"
        + timestamp
        + '\''
        + ", prices="
        + prices
        + '}';
  }
}
