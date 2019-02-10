package org.knowm.xchange.dragonex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class Ticker {

  public final BigDecimal closePrice;
  public final BigDecimal currentVolume;
  public final BigDecimal maxPrice;
  public final BigDecimal minPrice;
  public final BigDecimal openPrice;
  public final BigDecimal priceBase;
  public final BigDecimal priceChange;
  public final BigDecimal priceChangeRate;
  public final long timestamp;
  public final BigDecimal totalAmount;
  public final BigDecimal totalVolume;
  public final BigDecimal usdtAmount;
  public final long symbolId;

  public Ticker(
      @JsonProperty("close_price") BigDecimal closePrice,
      @JsonProperty("current_volume") BigDecimal currentVolume,
      @JsonProperty("max_price") BigDecimal maxPrice,
      @JsonProperty("min_price") BigDecimal minPrice,
      @JsonProperty("open_price") BigDecimal openPrice,
      @JsonProperty("price_base") BigDecimal priceBase,
      @JsonProperty("price_change") BigDecimal priceChange,
      @JsonProperty("price_change_rate") BigDecimal priceChangeRate,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("total_amount") BigDecimal totalAmount,
      @JsonProperty("total_volume") BigDecimal totalVolume,
      @JsonProperty("usdt_amount") BigDecimal usdtAmount,
      @JsonProperty("symbol_id") long symbolId) {
    this.closePrice = closePrice;
    this.currentVolume = currentVolume;
    this.maxPrice = maxPrice;
    this.minPrice = minPrice;
    this.openPrice = openPrice;
    this.priceBase = priceBase;
    this.priceChange = priceChange;
    this.priceChangeRate = priceChangeRate;
    this.timestamp = timestamp;
    this.totalAmount = totalAmount;
    this.totalVolume = totalVolume;
    this.usdtAmount = usdtAmount;
    this.symbolId = symbolId;
  }

  @Override
  public String toString() {
    return "Ticker ["
        + (closePrice != null ? "closePrice=" + closePrice + ", " : "")
        + (currentVolume != null ? "currentVolume=" + currentVolume + ", " : "")
        + (maxPrice != null ? "maxPrice=" + maxPrice + ", " : "")
        + (minPrice != null ? "minPrice=" + minPrice + ", " : "")
        + (openPrice != null ? "openPrice=" + openPrice + ", " : "")
        + (priceBase != null ? "priceBase=" + priceBase + ", " : "")
        + (priceChange != null ? "priceChange=" + priceChange + ", " : "")
        + (priceChangeRate != null ? "priceChangeRate=" + priceChangeRate + ", " : "")
        + "timestamp="
        + timestamp
        + ", "
        + (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "")
        + (totalVolume != null ? "totalVolume=" + totalVolume + ", " : "")
        + (usdtAmount != null ? "usdtAmount=" + usdtAmount + ", " : "")
        + "symbolId="
        + symbolId
        + "]";
  }
}
