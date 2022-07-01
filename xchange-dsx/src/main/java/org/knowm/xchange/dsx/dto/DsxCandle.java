package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class DsxCandle {

  private String timestamp;

  private BigDecimal open;

  private BigDecimal close;

  private BigDecimal min;

  private BigDecimal max;

  private BigDecimal volume;

  private BigDecimal volumeQuote;

  @JsonCreator
  public DsxCandle(
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("close") BigDecimal close,
      @JsonProperty("min") BigDecimal min,
      @JsonProperty("max") BigDecimal max,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("volumeQuote") BigDecimal volumeQuote) {
    this.timestamp = timestamp;
    this.open = open;
    this.close = close;
    this.min = min;
    this.max = max;
    this.volume = volume;
    this.volumeQuote = volumeQuote;
  }

  @Override
  public String toString() {
    return "DsxCandle [timestamp="
        + timestamp
        + ", open="
        + open
        + ", max="
        + max
        + ", min="
        + min
        + ", close="
        + close
        + ", volumeQuote="
        + volumeQuote
        + ", volume="
        + volume
        + "]";
  }

  public String getTimestamp() {
    return timestamp;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getMin() {
    return min;
  }

  public BigDecimal getMax() {
    return max;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVolumeQuote() {
    return volumeQuote;
  }
}
