package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BittrexV2Summary {
  private Integer openSellOrders;
  private LocalDateTime timeStamp;
  private BigDecimal last;
  private BigDecimal low;
  private LocalDateTime created;
  private BigDecimal prevDay;
  private Integer openBuyOrders;
  private BigDecimal volume;
  private BigDecimal baseVolume;
  private BigDecimal bid;
  private BigDecimal ask;
  private String marketName;
  private BigDecimal high;

  public BittrexV2Summary(
      @JsonProperty("OpenSellOrders") Integer openSellOrders,
      @JsonProperty("TimeStamp") String timeStamp,
      @JsonProperty("Last") BigDecimal last,
      @JsonProperty("Low") BigDecimal low,
      @JsonProperty("Created") String created,
      @JsonProperty("PrevDay") BigDecimal prevDay,
      @JsonProperty("OpenBuyOrders") Integer openBuyOrders,
      @JsonProperty("Volume") BigDecimal volume,
      @JsonProperty("BaseVolume") BigDecimal baseVolume,
      @JsonProperty("Bid") BigDecimal bid,
      @JsonProperty("Ask") BigDecimal ask,
      @JsonProperty("MarketName") String marketName,
      @JsonProperty("High") BigDecimal high) {
    this.openSellOrders = openSellOrders;
    this.timeStamp = LocalDateTime.parse(timeStamp);
    this.last = last;
    this.low = low;
    this.created = LocalDateTime.parse(created);
    this.prevDay = prevDay;
    this.openBuyOrders = openBuyOrders;
    this.volume = volume;
    this.baseVolume = baseVolume;
    this.bid = bid;
    this.ask = ask;
    this.marketName = marketName;
    this.high = high;
  }

  public Integer getOpenSellOrders() {
    return openSellOrders;
  }

  public void setOpenSellOrders(Integer openSellOrders) {
    this.openSellOrders = openSellOrders;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(LocalDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  public BigDecimal getLast() {
    return last;
  }

  public void setLast(BigDecimal last) {
    this.last = last;
  }

  public BigDecimal getLow() {
    return low;
  }

  public void setLow(BigDecimal low) {
    this.low = low;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public BigDecimal getPrevDay() {
    return prevDay;
  }

  public void setPrevDay(BigDecimal prevDay) {
    this.prevDay = prevDay;
  }

  public Integer getOpenBuyOrders() {
    return openBuyOrders;
  }

  public void setOpenBuyOrders(Integer openBuyOrders) {
    this.openBuyOrders = openBuyOrders;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public BigDecimal getBaseVolume() {
    return baseVolume;
  }

  public void setBaseVolume(BigDecimal baseVolume) {
    this.baseVolume = baseVolume;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public void setBid(BigDecimal bid) {
    this.bid = bid;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public void setAsk(BigDecimal ask) {
    this.ask = ask;
  }

  public String getMarketName() {
    return marketName;
  }

  public void setMarketName(String marketName) {
    this.marketName = marketName;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public void setHigh(BigDecimal high) {
    this.high = high;
  }

  @Override
  public String toString() {
    return "BittrexV2Summary{"
        + "openSellOrders="
        + openSellOrders
        + ", timeStamp="
        + timeStamp
        + ", last="
        + last
        + ", low="
        + low
        + ", created="
        + created
        + ", prevDay="
        + prevDay
        + ", openBuyOrders="
        + openBuyOrders
        + ", volume="
        + volume
        + ", baseVolume="
        + baseVolume
        + ", bid="
        + bid
        + ", ask="
        + ask
        + ", marketName='"
        + marketName
        + '\''
        + ", high="
        + high
        + '}';
  }
}
