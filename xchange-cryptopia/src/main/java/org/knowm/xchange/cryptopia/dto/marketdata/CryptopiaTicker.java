package org.knowm.xchange.cryptopia.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public final class CryptopiaTicker {

  private final long tradePairId;
  private final String label;
  private final BigDecimal ask;
  private final BigDecimal bid;
  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal volume;
  private final BigDecimal last;
  private final BigDecimal buyVolume;
  private final BigDecimal sellVolume;
  private final BigDecimal change;
  private final BigDecimal open;
  private final BigDecimal close;
  private final BigDecimal baseVolume;
  private final BigDecimal buyBaseVolume;
  private final BigDecimal sellBaseVolume;

  public CryptopiaTicker(
      @JsonProperty("TradePairId") long tradePairId,
      @JsonProperty("Label") String label,
      @JsonProperty("AskPrice") BigDecimal ask,
      @JsonProperty("BidPrice") BigDecimal bid,
      @JsonProperty("Low") BigDecimal low,
      @JsonProperty("High") BigDecimal high,
      @JsonProperty("Volume") BigDecimal volume,
      @JsonProperty("LastPrice") BigDecimal last,
      @JsonProperty("BuyVolume") BigDecimal buyVolume,
      @JsonProperty("SellVolume") BigDecimal sellVolume,
      @JsonProperty("Change") BigDecimal change,
      @JsonProperty("Open") BigDecimal open,
      @JsonProperty("Close") BigDecimal close,
      @JsonProperty("BaseVolume") BigDecimal baseVolume,
      @JsonProperty("BuyBaseVolume") BigDecimal buyBaseVolume,
      @JsonProperty("SellBaseVolume") BigDecimal sellBaseVolume) {
    this.tradePairId = tradePairId;
    this.label = label;
    this.ask = ask;
    this.bid = bid;
    this.low = low;
    this.high = high;
    this.volume = volume;
    this.last = last;
    this.buyVolume = buyVolume;
    this.sellVolume = sellVolume;
    this.change = change;
    this.open = open;
    this.close = close;
    this.baseVolume = baseVolume;
    this.buyBaseVolume = buyBaseVolume;
    this.sellBaseVolume = sellBaseVolume;
  }

  public long getTradePairId() {
    return tradePairId;
  }

  public String getLabel() {
    return label;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getBuyVolume() {
    return buyVolume;
  }

  public BigDecimal getSellVolume() {
    return sellVolume;
  }

  public BigDecimal getChange() {
    return change;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getBaseVolume() {
    return baseVolume;
  }

  public BigDecimal getBuyBaseVolume() {
    return buyBaseVolume;
  }

  public BigDecimal getSellBaseVolume() {
    return sellBaseVolume;
  }

  @Override
  public String toString() {
    return "CryptopiaTicker{"
        + "tradePairId="
        + tradePairId
        + ", label='"
        + label
        + '\''
        + ", ask="
        + ask
        + ", bid="
        + bid
        + ", low="
        + low
        + ", high="
        + high
        + ", volume="
        + volume
        + ", last="
        + last
        + ", buyVolume="
        + buyVolume
        + ", sellVolume="
        + sellVolume
        + ", change="
        + change
        + ", open="
        + open
        + ", close="
        + close
        + ", baseVolume="
        + baseVolume
        + ", buyBaseVolume="
        + buyBaseVolume
        + ", sellBaseVolume="
        + sellBaseVolume
        + '}';
  }
}
