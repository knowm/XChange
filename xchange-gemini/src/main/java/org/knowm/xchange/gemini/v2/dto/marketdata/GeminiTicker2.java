package org.knowm.xchange.gemini.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;

public class GeminiTicker2 {
  private final String symbol;
  private final BigDecimal open; // day open
  private final BigDecimal high; // 24h high
  private final BigDecimal low; // 24h low
  private final BigDecimal close; // day close
  private final BigDecimal[] changes;
  private final BigDecimal bid;
  private final BigDecimal ask;

  public GeminiTicker2(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("close") BigDecimal close,
      @JsonProperty("changes") BigDecimal[] changes,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask) {

    this.symbol = symbol;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.changes = changes;
    this.bid = bid;
    this.ask = ask;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal[] getChanges() {
    return changes;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  @Override
  public String toString() {
    return "GeminiTicker2{"
        + "symbol='"
        + symbol
        + '\''
        + ", open="
        + open
        + ", high="
        + high
        + ", low="
        + low
        + ", close="
        + close
        + ", changes="
        + Arrays.toString(changes)
        + ", bid="
        + bid
        + ", ask="
        + ask
        + '}';
  }
}
