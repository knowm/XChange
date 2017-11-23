package org.knowm.xchange.ccex.dto.ticker;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CCEXPriceResponse {

  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal avg;
  private BigDecimal lastbuy;
  private BigDecimal lastsell;
  private BigDecimal buy;
  private BigDecimal sell;
  private BigDecimal lastprice;
  private BigDecimal buysupport;
  private int updated;

  public CCEXPriceResponse(@JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("avg") BigDecimal avg,
      @JsonProperty("lastbuy") BigDecimal lastbuy, @JsonProperty("lastsell") BigDecimal lastsell, @JsonProperty("buy") BigDecimal buy,
      @JsonProperty("sell") BigDecimal sell, @JsonProperty("lastprice") BigDecimal lastprice, @JsonProperty("buysupport") BigDecimal buysupport,
      @JsonProperty("updated") int updated) {
    super();
    this.high = high;
    this.low = low;
    this.avg = avg;
    this.lastbuy = lastbuy;
    this.lastsell = lastsell;
    this.buy = buy;
    this.sell = sell;
    this.lastprice = lastprice;
    this.buysupport = buysupport;
    this.updated = updated;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public void setHigh(BigDecimal high) {
    this.high = high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public void setLow(BigDecimal low) {
    this.low = low;
  }

  public BigDecimal getAvg() {
    return avg;
  }

  public void setAvg(BigDecimal avg) {
    this.avg = avg;
  }

  public BigDecimal getLastbuy() {
    return lastbuy;
  }

  public void setLastbuy(BigDecimal lastbuy) {
    this.lastbuy = lastbuy;
  }

  public BigDecimal getLastsell() {
    return lastsell;
  }

  public void setLastsell(BigDecimal lastsell) {
    this.lastsell = lastsell;
  }

  public BigDecimal getBuy() {
    return buy;
  }

  public void setBuy(BigDecimal buy) {
    this.buy = buy;
  }

  public BigDecimal getSell() {
    return sell;
  }

  public void setSell(BigDecimal sell) {
    this.sell = sell;
  }

  public BigDecimal getLastprice() {
    return lastprice;
  }

  public void setLastprice(BigDecimal lastprice) {
    this.lastprice = lastprice;
  }

  public BigDecimal getBuysupport() {
    return buysupport;
  }

  public void setBuysupport(BigDecimal buysupport) {
    this.buysupport = buysupport;
  }

  public int getUpdated() {
    return updated;
  }

  public void setUpdated(int updated) {
    this.updated = updated;
  }

  @Override
  public String toString() {
    return "CCEXPriceResponse [high=" + high + ", low=" + low + ", avg=" + avg + ", lastbuy=" + lastbuy + ", lastsell=" + lastsell + ", buy=" + buy
        + ", sell=" + sell + ", lastprice=" + lastprice + ", buysupport=" + buysupport + ", updated=" + updated + "]";
  }
}
