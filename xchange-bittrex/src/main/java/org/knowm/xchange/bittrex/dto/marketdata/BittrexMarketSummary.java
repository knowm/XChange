package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BittrexMarketSummary {

  private BigDecimal ask;
  private BigDecimal baseVolume;
  private BigDecimal bid;
  private String created;
  private String displayMarketName;
  private BigDecimal high;
  private BigDecimal last;
  private BigDecimal low;
  private String marketName;
  private int openBuyOrders;
  private int openSellOrders;
  private BigDecimal prevDay;
  private String timeStamp;
  private BigDecimal volume;

  public BittrexMarketSummary(
      @JsonProperty("Ask") BigDecimal ask,
      @JsonProperty("BaseVolume") BigDecimal baseVolume,
      @JsonProperty("Bid") BigDecimal bid,
      @JsonProperty("Created") String created,
      @JsonProperty("DisplayMarketName") String displayMarketName,
      @JsonProperty("High") BigDecimal high,
      @JsonProperty("Last") BigDecimal last,
      @JsonProperty("Low") BigDecimal low,
      @JsonProperty("MarketName") String marketName,
      @JsonProperty("OpenBuyOrders") int openBuyOrders,
      @JsonProperty("OpenSellOrders") int openSellOrders,
      @JsonProperty("PrevDay") BigDecimal prevDay,
      @JsonProperty("TimeStamp") String timeStamp,
      @JsonProperty("Volume") BigDecimal volume) {

    this.ask = ask;
    this.baseVolume = baseVolume;
    this.bid = bid;
    this.created = created;
    this.displayMarketName = displayMarketName;
    this.high = high;
    this.last = last;
    this.low = low;
    this.marketName = marketName;
    this.openBuyOrders = openBuyOrders;
    this.openSellOrders = openSellOrders;
    this.prevDay = prevDay;
    this.timeStamp = timeStamp;
    this.volume = volume;
  }

  public BigDecimal getAsk() {

    return this.ask;
  }

  public void setAsk(BigDecimal ask) {

    this.ask = ask;
  }

  public BigDecimal getBaseVolume() {

    return this.baseVolume;
  }

  public void setBaseVolume(BigDecimal baseVolume) {

    this.baseVolume = baseVolume;
  }

  public BigDecimal getBid() {

    return this.bid;
  }

  public void setBid(BigDecimal bid) {

    this.bid = bid;
  }

  public String getCreated() {

    return this.created;
  }

  public void setCreated(String created) {

    this.created = created;
  }

  public String getDisplayMarketName() {

    return this.displayMarketName;
  }

  public void setDisplayMarketName(String displayMarketName) {

    this.displayMarketName = displayMarketName;
  }

  public BigDecimal getHigh() {

    return this.high;
  }

  public void setHigh(BigDecimal high) {

    this.high = high;
  }

  public BigDecimal getLast() {

    return this.last;
  }

  public void setLast(BigDecimal last) {

    this.last = last;
  }

  public BigDecimal getLow() {

    return this.low;
  }

  public void setLow(BigDecimal low) {

    this.low = low;
  }

  public String getMarketName() {

    return this.marketName;
  }

  public void setMarketName(String marketName) {

    this.marketName = marketName;
  }

  public int getOpenBuyOrders() {

    return this.openBuyOrders;
  }

  public void setOpenBuyOrders(int openBuyOrders) {

    this.openBuyOrders = openBuyOrders;
  }

  public int getOpenSellOrders() {

    return this.openSellOrders;
  }

  public void setOpenSellOrders(int openSellOrders) {

    this.openSellOrders = openSellOrders;
  }

  public BigDecimal getPrevDay() {

    return this.prevDay;
  }

  public void setPrevDay(BigDecimal prevDay) {

    this.prevDay = prevDay;
  }

  public String getTimeStamp() {

    return this.timeStamp;
  }

  public void setTimeStamp(String timeStamp) {

    this.timeStamp = timeStamp;
  }

  public BigDecimal getVolume() {

    return this.volume;
  }

  public void setVolume(BigDecimal volume) {

    this.volume = volume;
  }

  @Override
  public String toString() {

    return "BittrexMarketSummary [ask="
        + ask
        + ", baseVolume="
        + baseVolume
        + ", bid="
        + bid
        + ", created="
        + created
        + ", displayMarketName="
        + displayMarketName
        + ", high="
        + high
        + ", last="
        + last
        + ", low="
        + low
        + ", marketName="
        + marketName
        + ", openBuyOrders="
        + openBuyOrders
        + ", openSellOrders="
        + openSellOrders
        + ", prevDay="
        + prevDay
        + ", timeStamp="
        + timeStamp
        + ", volume="
        + volume
        + "]";
  }
}
