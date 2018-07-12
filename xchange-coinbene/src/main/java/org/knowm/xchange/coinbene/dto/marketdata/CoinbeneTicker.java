package org.knowm.xchange.coinbene.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.coinbene.dto.CoinbeneResponse;

public class CoinbeneTicker {
  private final String symbol;
  private final BigDecimal dayHigh;
  private final BigDecimal dayLow;
  private final BigDecimal last;
  private final BigDecimal ask;
  private final BigDecimal bid;
  private final BigDecimal dayVolume;
  private final BigDecimal dayAmount;

  public CoinbeneTicker(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("24hrHigh") BigDecimal dayHigh,
      @JsonProperty("24hrLow") BigDecimal dayLow,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("24hrVol") BigDecimal dayVolume,
      @JsonProperty("24hrAmt") BigDecimal dayAmount) {
    this.symbol = symbol;
    this.dayHigh = dayHigh;
    this.dayLow = dayLow;
    this.last = last;
    this.ask = ask;
    this.bid = bid;
    this.dayVolume = dayVolume;
    this.dayAmount = dayAmount;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getDayHigh() {
    return dayHigh;
  }

  public BigDecimal getDayLow() {
    return dayLow;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getDayVolume() {
    return dayVolume;
  }

  public BigDecimal getDayAmount() {
    return dayAmount;
  }

  @Override
  public String toString() {
    return "CoinbeneTicker{"
        + "symbol='"
        + symbol
        + '\''
        + ", dayHigh="
        + dayHigh
        + ", dayLow="
        + dayLow
        + ", last="
        + last
        + ", ask="
        + ask
        + ", bid="
        + bid
        + ", dayVolume="
        + dayVolume
        + ", dayAmount="
        + dayAmount
        + '}';
  }

  public static class Container extends CoinbeneResponse {
    private final List<CoinbeneTicker> tickers;
    private final long timestamp;

    public Container(
        @JsonProperty("ticker") List<CoinbeneTicker> tickers,
        @JsonProperty("timestamp") long timestamp) {
      this.tickers = tickers;
      this.timestamp = timestamp;
    }

    public CoinbeneTicker getTicker() {
      return tickers.get(0);
    }

    public long getTimestamp() {
      return timestamp;
    }
  }
}
