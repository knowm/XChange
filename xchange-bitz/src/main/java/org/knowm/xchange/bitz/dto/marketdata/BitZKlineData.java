package org.knowm.xchange.bitz.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitZKlineData {
  private final BitZKline[] bars;
  private final String resolution;
  private final String symbol;
  private final long fromTime;
  private final long toTime;
  private final int size;

  public BitZKlineData(
      @JsonProperty("bars") BitZKline[] bars,
      @JsonProperty("resolution") String resolution,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("from") long fromTime,
      @JsonProperty("to") long toTime,
      @JsonProperty("size") int size) {
    this.bars = bars;
    this.resolution = resolution;
    this.symbol = symbol;
    this.fromTime = fromTime;
    this.toTime = toTime;
    this.size = size;
  }

  public BitZKline[] getBars() {
    return bars;
  }

  public String getResolution() {
    return resolution;
  }

  public String getSymbol() {
    return symbol;
  }

  public long getFromTime() {
    return fromTime;
  }

  public long getToTime() {
    return toTime;
  }

  public int getSize() {
    return size;
  }
}
