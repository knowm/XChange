package org.knowm.xchange.bitmex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

@JsonPropertyOrder({
  "timestamp",
  "symbol",
  "open",
  "high",
  "low",
  "close",
  "trades",
  "volume",
  "vwap",
  "lastSize",
  "turnover",
  "homeNotional",
  "foreignNotional"
})
public class BitmexKline {
  @JsonProperty("timestamp")
  private final String timestamp;

  @JsonProperty("symbol")
  private final String symbol;

  @JsonProperty("open")
  private final BigDecimal open;

  @JsonProperty("high")
  private final BigDecimal high;

  @JsonProperty("low")
  private final BigDecimal low;

  @JsonProperty("close")
  private final BigDecimal close;

  @JsonProperty("trades")
  private final BigDecimal trades;

  @JsonProperty("volume")
  private final BigDecimal volume;

  @JsonProperty("vwap")
  private final BigDecimal vwap;

  @JsonProperty("lastSize")
  private final Long lastSize;

  @JsonProperty("turnover")
  private final Long turnover;

  @JsonProperty("homeNotional")
  private final BigDecimal homeNotional;

  @JsonProperty("foreignNotional")
  private final BigDecimal foreignNotional;

  public BitmexKline(
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("close") BigDecimal close,
      @JsonProperty("trades") BigDecimal trades,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("vwap") BigDecimal vwap,
      @JsonProperty("lastSize") Long lastSize,
      @JsonProperty("turnover") Long turnover,
      @JsonProperty("homeNotional") BigDecimal homeNotional,
      @JsonProperty("foreignNotional") BigDecimal foreignNotional) {
    this.timestamp = timestamp;
    this.symbol = symbol;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.trades = trades;
    this.volume = volume;
    this.vwap = vwap;
    this.lastSize = lastSize;
    this.turnover = turnover;
    this.homeNotional = homeNotional;
    this.foreignNotional = foreignNotional;
  }

  @JsonProperty("timestamp")
  public String getTimestamp() {
    return timestamp;
  }

  @JsonProperty("symbol")
  public String getSymbol() {
    return symbol;
  }

  @JsonProperty("open")
  public BigDecimal getOpen() {
    return open;
  }

  @JsonProperty("high")
  public BigDecimal getHigh() {
    return high;
  }

  @JsonProperty("low")
  public BigDecimal getLow() {
    return low;
  }

  @JsonProperty("close")
  public BigDecimal getClose() {
    return close;
  }

  @JsonProperty("trades")
  public BigDecimal getTrades() {
    return trades;
  }

  @JsonProperty("volume")
  public BigDecimal getVolume() {
    return volume;
  }

  @JsonProperty("vwap")
  public BigDecimal getVwap() {
    return vwap;
  }

  @JsonProperty("lastSize")
  public Long getLastSize() {
    return lastSize;
  }

  @JsonProperty("turnover")
  public Long getTurnover() {
    return turnover;
  }

  @JsonProperty("homeNotional")
  public BigDecimal getHomeNotional() {
    return homeNotional;
  }

  @JsonProperty("foreignNotional")
  public BigDecimal getForeignNotional() {
    return foreignNotional;
  }

  @Override
  public String toString() {
    return "BitmexKline [timestamp="
        + timestamp
        + ", symbol="
        + symbol
        + ", open="
        + open
        + ", high="
        + high
        + ", low="
        + low
        + ", close="
        + close
        + ", trades="
        + trades
        + ", volume="
        + volume
        + ", vwap="
        + vwap
        + ", lastSize="
        + lastSize
        + ", turnover="
        + turnover
        + ", homeNotional="
        + homeNotional
        + ", foreignNotional="
        + foreignNotional
        + "]";
  }
}
