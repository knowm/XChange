package org.knowm.xchange.lykke.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LykkeOrderBook {

  @JsonProperty("AssetPair")
  private final String assetPair;

  @JsonProperty("IsBuy")
  private final boolean isBuy;

  @JsonProperty("Timestamp")
  private final String timestamp;

  @JsonProperty("Prices")
  private final List<LykkePrices> prices;

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

  public boolean isBuy() {
    return isBuy;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public List<LykkePrices> getPrices() {
    return prices;
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
