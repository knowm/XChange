package org.knowm.xchange.globitex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "symbol",
  "ask",
  "bid",
  "last",
  "low",
  "high",
  "open",
  "volume",
  "volumeQuote",
  "timestamp"
})
public class GlobitexTicker implements Serializable {

  @JsonProperty("symbol")
  private final String symbol;

  @JsonProperty("ask")
  private final BigDecimal ask;

  @JsonProperty("bid")
  private final BigDecimal bid;

  @JsonProperty("last")
  private final BigDecimal last;

  @JsonProperty("low")
  private final BigDecimal low;

  @JsonProperty("high")
  private final BigDecimal high;

  @JsonProperty("open")
  private final BigDecimal open;

  @JsonProperty("volume")
  private final BigDecimal volume;

  @JsonProperty("volumeQuote")
  private final BigDecimal volumeQuote;

  @JsonProperty("timestamp")
  private final long timestamp;

  /**
   * @param timestamp
   * @param open
   * @param last
   * @param symbol
   * @param volume
   * @param high
   * @param low
   * @param ask
   * @param bid
   * @param volumeQuote
   */
  public GlobitexTicker(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("volumeQuote") BigDecimal volumeQuote,
      @JsonProperty("timestamp") long timestamp) {
    super();
    this.symbol = symbol;
    this.ask = ask;
    this.bid = bid;
    this.last = last;
    this.low = low;
    this.high = high;
    this.open = open;
    this.volume = volume;
    this.volumeQuote = volumeQuote;
    this.timestamp = timestamp;
  }

  public String getSymbol() {
    return symbol;
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

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVolumeQuote() {
    return volumeQuote;
  }

  public long getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "GlobitexTicker{"
        + "symbol='"
        + symbol
        + '\''
        + ", ask='"
        + ask
        + '\''
        + ", bid='"
        + bid
        + '\''
        + ", last='"
        + last
        + '\''
        + ", low='"
        + low
        + '\''
        + ", high='"
        + high
        + '\''
        + ", open='"
        + open
        + '\''
        + ", volume='"
        + volume
        + '\''
        + ", volumeQuote='"
        + volumeQuote
        + '\''
        + ", timestamp="
        + timestamp
        + '}';
  }
}
