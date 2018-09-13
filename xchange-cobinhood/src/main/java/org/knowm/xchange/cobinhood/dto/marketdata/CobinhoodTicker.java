package org.knowm.xchange.cobinhood.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CobinhoodTicker {
  private final String tradingPairId;
  private final long timestamp;
  private final BigDecimal dayHigh;
  private final BigDecimal dayLow;
  private final BigDecimal dayOpen;
  private final BigDecimal dayVolume;
  private final BigDecimal lastTradePrice;
  private final BigDecimal highestBid;
  private final BigDecimal lowestAsk;

  public CobinhoodTicker(
      @JsonProperty("trading_pair_id") String tradingPairId,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("24h_high") BigDecimal dayHigh,
      @JsonProperty("24h_low") BigDecimal dayLow,
      @JsonProperty("24h_open") BigDecimal dayOpen,
      @JsonProperty("24h_volume") BigDecimal dayVolume,
      @JsonProperty("last_trade_price") BigDecimal lastTradePrice,
      @JsonProperty("highest_bid") BigDecimal highestBid,
      @JsonProperty("lowest_ask") BigDecimal lowestAsk) {
    this.tradingPairId = tradingPairId;
    this.timestamp = timestamp;
    this.dayHigh = dayHigh;
    this.dayLow = dayLow;
    this.dayOpen = dayOpen;
    this.dayVolume = dayVolume;
    this.lastTradePrice = lastTradePrice;
    this.highestBid = highestBid;
    this.lowestAsk = lowestAsk;
  }

  public String getTradingPairId() {
    return tradingPairId;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public BigDecimal getDayHigh() {
    return dayHigh;
  }

  public BigDecimal getDayLow() {
    return dayLow;
  }

  public BigDecimal getDayOpen() {
    return dayOpen;
  }

  public BigDecimal getDayVolume() {
    return dayVolume;
  }

  public BigDecimal getLastTradePrice() {
    return lastTradePrice;
  }

  public BigDecimal getHighestBid() {
    return highestBid;
  }

  public BigDecimal getLowestAsk() {
    return lowestAsk;
  }

  @Override
  public String toString() {
    return "CobinhoodTicker{"
        + "tradingPairId='"
        + tradingPairId
        + '\''
        + ", timestamp="
        + timestamp
        + ", dayHigh="
        + dayHigh
        + ", dayLow="
        + dayLow
        + ", dayOpen="
        + dayOpen
        + ", dayVolume="
        + dayVolume
        + ", lastTradePrice="
        + lastTradePrice
        + ", highestBid="
        + highestBid
        + ", lowestAsk="
        + lowestAsk
        + '}';
  }

  public static class Container {
    private final CobinhoodTicker ticker;

    public Container(@JsonProperty("ticker") CobinhoodTicker ticker) {
      this.ticker = ticker;
    }

    public CobinhoodTicker getTicker() {
      return ticker;
    }
  }
}
