package org.knowm.xchange.mexbt.dto.streaming;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeXBTStreamingTicker {

  private final String messageType;
  private final String prodPair;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal last;
  private final BigDecimal volume;
  private final BigDecimal volume24hrs;
  private final BigDecimal volume24hrsProduct2;
  private final BigDecimal total24HrQtyTraded;
  private final BigDecimal total24HrProduct2Traded;
  private final long total24HrNumTrades;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final long buyOrderCount;
  private final long sellOrderCount;

  public MeXBTStreamingTicker(@JsonProperty("messageType") String messageType, @JsonProperty("prodPair") String prodPair,
      @JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("last") BigDecimal last,
      @JsonProperty("volume") BigDecimal volume, @JsonProperty("volume24hrs") BigDecimal volume24hrs,
      @JsonProperty("volume24hrsProduct2") BigDecimal volume24hrsProduct2, @JsonProperty("Total24HrQtyTraded") BigDecimal total24HrQtyTraded,
      @JsonProperty("Total24HrProduct2Traded") BigDecimal total24HrProduct2Traded, @JsonProperty("Total24HrNumTrades") long total24HrNumTrades,
      @JsonProperty("bid") BigDecimal bid, @JsonProperty("ask") BigDecimal ask, @JsonProperty("buyOrderCount") long buyOrderCount,
      @JsonProperty("sellOrderCount") long sellOrderCount) {
    this.messageType = messageType;
    this.prodPair = prodPair;
    this.high = high;
    this.low = low;
    this.last = last;
    this.volume = volume;
    this.volume24hrs = volume24hrs;
    this.volume24hrsProduct2 = volume24hrsProduct2;
    this.total24HrQtyTraded = total24HrQtyTraded;
    this.total24HrProduct2Traded = total24HrProduct2Traded;
    this.total24HrNumTrades = total24HrNumTrades;
    this.bid = bid;
    this.ask = ask;
    this.buyOrderCount = buyOrderCount;
    this.sellOrderCount = sellOrderCount;
  }

  public String getMessageType() {
    return messageType;
  }

  public String getProdPair() {
    return prodPair;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVolume24hrs() {
    return volume24hrs;
  }

  public BigDecimal getVolume24hrsProduct2() {
    return volume24hrsProduct2;
  }

  public BigDecimal getTotal24HrQtyTraded() {
    return total24HrQtyTraded;
  }

  public BigDecimal getTotal24HrProduct2Traded() {
    return total24HrProduct2Traded;
  }

  public long getTotal24HrNumTrades() {
    return total24HrNumTrades;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public long getBuyOrderCount() {
    return buyOrderCount;
  }

  public long getSellOrderCount() {
    return sellOrderCount;
  }

}
