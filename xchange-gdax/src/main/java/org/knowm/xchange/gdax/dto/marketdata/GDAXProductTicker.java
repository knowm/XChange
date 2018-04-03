package org.knowm.xchange.gdax.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Created by Yingzhe on 4/4/2015. */
public class GDAXProductTicker {

  private final String tradeId;
  private final BigDecimal price;
  private final BigDecimal size;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal volume;
  private final String time;

  public GDAXProductTicker(
      @JsonProperty("trade_id") String tradeId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("time") String time) {

    this.tradeId = tradeId;
    this.price = price;
    this.size = size;
    this.bid = bid;
    this.ask = ask;
    this.volume = volume;
    this.time = time;
  }

  public String getTradeId() {
    return tradeId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public String getTime() {
    return time;
  }

  @Override
  public String toString() {
    return "GDAXProductTicker [tradeId="
        + tradeId
        + ", price="
        + price
        + ", size="
        + size
        + ", bid="
        + bid
        + ", ask="
        + ask
        + ", volume="
        + volume
        + ", time="
        + time
        + "]";
  }
}
