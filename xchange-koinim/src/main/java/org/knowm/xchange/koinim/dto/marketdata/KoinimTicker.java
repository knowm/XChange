package org.knowm.xchange.koinim.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author ahmet.oz */
public final class KoinimTicker {

  private final BigDecimal sell;
  private final BigDecimal high;
  private final BigDecimal buy;
  private final BigDecimal change_rate;
  private final BigDecimal bid;
  private final BigDecimal wavg;
  private final BigDecimal last_order;
  private final BigDecimal volume;
  private final BigDecimal low;
  private final BigDecimal ask;
  private final BigDecimal avg;

  public KoinimTicker(
      @JsonProperty("sell") BigDecimal sell,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("buy") BigDecimal buy,
      @JsonProperty("change_rate") BigDecimal change_rate,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("wavg") BigDecimal wavg,
      @JsonProperty("last_order") BigDecimal last_order,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("avg") BigDecimal avg) {
    this.sell = sell;
    this.high = high;
    this.buy = buy;
    this.change_rate = change_rate;
    this.bid = bid;
    this.wavg = wavg;
    this.last_order = last_order;
    this.volume = volume;
    this.low = low;
    this.ask = ask;
    this.avg = avg;
  }

  public BigDecimal getAvg() {
    return avg;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getSell() {
    return sell;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getBuy() {
    return buy;
  }

  public BigDecimal getChangeRate() {
    return change_rate;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getWavg() {
    return wavg;
  }

  public BigDecimal getLastOrder() {
    return last_order;
  }

  @Override
  public String toString() {
    return "KoinimTicker {"
        + "sell="
        + sell
        + ", high="
        + high
        + ", buy="
        + buy
        + ", change_rate="
        + change_rate
        + ", bid="
        + bid
        + ", wavg="
        + wavg
        + ", last_order="
        + last_order
        + ", volume="
        + volume
        + ", low="
        + low
        + ", ask="
        + ask
        + ", avg="
        + avg
        + '}';
  }

  public BigDecimal getVolume() {
    return volume;
  }
}
