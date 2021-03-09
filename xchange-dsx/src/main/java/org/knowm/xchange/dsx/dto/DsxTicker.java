package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class DsxTicker {

  private final BigDecimal ask;
  private final BigDecimal bid;
  private final BigDecimal last;
  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal open;
  private final String symbol;
  private final Date timestamp;
  private final BigDecimal volume;
  private final BigDecimal volumeQuote;

  public DsxTicker(
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("timestamp") Date timestamp,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("volumeQuote") BigDecimal volumeQuote) {

    this.ask = ask;
    this.bid = bid;
    this.last = last;
    this.low = low;
    this.high = high;
    this.open = open;
    this.volume = volume;
    this.volumeQuote = volumeQuote;
    this.timestamp = timestamp;
    this.symbol = symbol;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public String getSymbol() {
    return symbol;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVolumeQuote() {
    return volumeQuote;
  }

  @Override
  public String toString() {
    return "DsxTicker{"
        + "ask="
        + ask
        + ", bid="
        + bid
        + ", last="
        + last
        + ", low="
        + low
        + ", high="
        + high
        + ", open="
        + open
        + ", symbol='"
        + symbol
        + '\''
        + ", timestamp="
        + timestamp
        + ", volume="
        + volume
        + ", volumeQuote="
        + volumeQuote
        + '}';
  }
}
