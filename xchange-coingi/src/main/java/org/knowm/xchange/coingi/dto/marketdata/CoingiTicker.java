package org.knowm.xchange.coingi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoingiTicker {
  private BigDecimal open;
  private BigDecimal close;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal vwap;
  private BigDecimal volume;
  private Long timestamp;

  public CoingiTicker(
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("close") BigDecimal close,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("vwap") BigDecimal vwap,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("timestamp") Long timestamp) {
    this.open = open;
    this.close = close;
    this.high = high;
    this.low = low;
    this.vwap = vwap;
    this.volume = volume;
    this.timestamp = timestamp;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getVwap() {
    return vwap;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public Long getTimestamp() {
    return timestamp;
  }
}
