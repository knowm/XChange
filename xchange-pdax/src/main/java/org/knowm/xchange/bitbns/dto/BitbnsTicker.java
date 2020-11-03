package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitbnsTicker {

  private PdaxTickerPrice avg;
  private PdaxTickerPrice buy;
  private PdaxTickerPrice high;
  private PdaxTickerPrice last;
  private PdaxTickerPrice low;
  private PdaxTickerPrice sell;
  private PdaxTickerPrice vol;
  private PdaxTickerPrice vwap;
  private long dataUpdateTime;
  private long now;

  public BitbnsTicker(
      @JsonProperty("avg") PdaxTickerPrice avg,
      @JsonProperty("buy") PdaxTickerPrice buy,
      @JsonProperty("high") PdaxTickerPrice high,
      @JsonProperty("last") PdaxTickerPrice last,
      @JsonProperty("low") PdaxTickerPrice low,
      @JsonProperty("sell") PdaxTickerPrice sell,
      @JsonProperty("vol") PdaxTickerPrice vol,
      @JsonProperty("vwap") PdaxTickerPrice vwap,
      @JsonProperty("dataUpdateTime") long dataUpdateTime,
      @JsonProperty("now") long now) {
    this.avg = avg;
    this.buy = buy;
    this.high = high;
    this.last = last;
    this.low = low;
    this.sell = sell;
    this.vol = vol;
    this.vwap = vwap;
    this.dataUpdateTime = dataUpdateTime;
    this.now = now;
  }

  public PdaxTickerPrice getAvg() {
    return avg;
  }

  public void setAvg(PdaxTickerPrice avg) {
    this.avg = avg;
  }

  public PdaxTickerPrice getBuy() {
    return buy;
  }

  public void setBuy(PdaxTickerPrice buy) {
    this.buy = buy;
  }

  public PdaxTickerPrice getHigh() {
    return high;
  }

  public void setHigh(PdaxTickerPrice high) {
    this.high = high;
  }

  public PdaxTickerPrice getLast() {
    return last;
  }

  public void setLast(PdaxTickerPrice last) {
    this.last = last;
  }

  public PdaxTickerPrice getLow() {
    return low;
  }

  public void setLow(PdaxTickerPrice low) {
    this.low = low;
  }

  public PdaxTickerPrice getSell() {
    return sell;
  }

  public void setSell(PdaxTickerPrice sell) {
    this.sell = sell;
  }

  public PdaxTickerPrice getVol() {
    return vol;
  }

  public void setVol(PdaxTickerPrice vol) {
    this.vol = vol;
  }

  public PdaxTickerPrice getVwap() {
    return vwap;
  }

  public void setVwap(PdaxTickerPrice vwap) {
    this.vwap = vwap;
  }

  public long getDataUpdateTime() {
    return dataUpdateTime;
  }

  public void setDataUpdateTime(long dataUpdateTime) {
    this.dataUpdateTime = dataUpdateTime;
  }

  public long getNow() {
    return now;
  }

  public void setNow(long now) {
    this.now = now;
  }

  @Override
  public String toString() {
    return "BitbnsTicker [avg="
        + avg
        + ", buy="
        + buy
        + ", high="
        + high
        + ", last="
        + last
        + ", low="
        + low
        + ", sell="
        + sell
        + ", vol="
        + vol
        + ", vwap="
        + vwap
        + ", dataUpdateTime="
        + dataUpdateTime
        + ", now="
        + now
        + "]";
  }
}
