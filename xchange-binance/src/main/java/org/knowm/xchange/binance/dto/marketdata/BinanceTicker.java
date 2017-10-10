package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BinanceTicker {
  private final String priceChange;
  private final String priceChangePercent;
  private final BigDecimal weightedAvgPrice;
  private final BigDecimal prevClosePrice;
  private final BigDecimal lastPrice;
  private final BigDecimal bidPrice;
  private final BigDecimal askPrice;
  private final BigDecimal openPrice;
  private final BigDecimal highPrice;
  private final BigDecimal lowPrice;
  private final BigDecimal volume;
  private final BigDecimal openTime;
  private final BigDecimal closeTime;
  private final BigDecimal fristId;
  private final BigDecimal lastId;
  private final BigDecimal count;

  public BinanceTicker(@JsonProperty("priceChange") String priceChange, @JsonProperty("priceChangePercent") String priceChangePercent,
      @JsonProperty("weightedAvgPrice") BigDecimal weightedAvgPrice, @JsonProperty("prevClosePrice") BigDecimal prevClosePrice,
      @JsonProperty("lastPrice") BigDecimal lastPrice, @JsonProperty("bidPrice") BigDecimal bidPrice,
      @JsonProperty("askPrice") BigDecimal askPrice, @JsonProperty("openPrice") BigDecimal openPrice,
      @JsonProperty("highPrice") BigDecimal highPrice, @JsonProperty("lowPrice") BigDecimal lowPrice,
      @JsonProperty("volume") BigDecimal volume, @JsonProperty("openTime") BigDecimal openTime,
      @JsonProperty("closeTime") BigDecimal closeTime, @JsonProperty("fristId") BigDecimal fristId,
      @JsonProperty("lastId") BigDecimal lastId, @JsonProperty("count") BigDecimal count) {
    this.priceChange = priceChange;
    this.priceChangePercent = priceChangePercent;
    this.weightedAvgPrice = weightedAvgPrice;
    this.prevClosePrice = prevClosePrice;
    this.lastPrice = lastPrice;
    this.bidPrice = bidPrice;
    this.askPrice = askPrice;
    this.openPrice = openPrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.volume = volume;
    this.openTime = openTime;
    this.closeTime = closeTime;
    this.fristId = fristId;
    this.lastId = lastId;
    this.count = count;
  }

  public String getPriceChange() {
    return priceChange;
  }

  public String getPriceChangePercent() {
    return priceChangePercent;
  }

  public BigDecimal getWeightedAvgPrice() {
    return weightedAvgPrice;
  }

  public BigDecimal getPrevClosePrice() {
    return prevClosePrice;
  }

  public BigDecimal getLastPrice() {
    return lastPrice;
  }

  public BigDecimal getBidPrice() {
    return bidPrice;
  }

  public BigDecimal getAskPrice() {
    return askPrice;
  }

  public BigDecimal getOpenPrice() {
    return openPrice;
  }

  public BigDecimal getHighPrice() {
    return highPrice;
  }

  public BigDecimal getLowPrice() {
    return lowPrice;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getOpenTime() {
    return openTime;
  }

  public BigDecimal getCloseTime() {
    return closeTime;
  }

  public BigDecimal getFristId() {
    return fristId;
  }

  public BigDecimal getLastId() {
    return lastId;
  }

  public BigDecimal getCount() {
    return count;
  }

  @Override
  public String toString() {
    return "BinanceTicker{" +
        "priceChange='" + priceChange + '\'' +
        ", priceChangePercent='" + priceChangePercent + '\'' +
        ", weightedAvgPrice=" + weightedAvgPrice +
        ", prevClosePrice=" + prevClosePrice +
        ", lastPrice=" + lastPrice +
        ", bidPrice=" + bidPrice +
        ", askPrice=" + askPrice +
        ", openPrice=" + openPrice +
        ", highPrice=" + highPrice +
        ", lowPrice=" + lowPrice +
        ", volume=" + volume +
        ", openTime=" + openTime +
        ", closeTime=" + closeTime +
        ", fristId=" + fristId +
        ", lastId=" + lastId +
        ", count=" + count +
        '}';
  }
}
