package org.knowm.xchange.exx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;

// -------------------------
public class EXXTicker extends EXXTickerResponse {

  @JsonProperty("vol")
  private BigDecimal vol;

  @JsonProperty("last")
  private BigDecimal last;

  @JsonProperty("sell")
  private BigDecimal sell;

  @JsonProperty("buy")
  private BigDecimal buy;

  @JsonProperty("weekRiseRate")
  private BigDecimal weekRiseRate;

  @JsonProperty("riseRate")
  private BigDecimal riseRate;

  @JsonProperty("high")
  private BigDecimal high;

  @JsonProperty("low")
  private BigDecimal low;

  @JsonProperty("monthRiseRate")
  private BigDecimal monthRiseRate;

  /** No args constructor for use in serialization */
  public EXXTicker() {}

  /**
   * @param vol
   * @param last
   * @param buy
   * @param sell
   * @param weekRiseRate
   * @param riseRate
   * @param high
   * @param low
   * @param monthRiseRate
   */
  public EXXTicker(
      BigDecimal vol,
      BigDecimal last,
      BigDecimal sell,
      BigDecimal buy,
      BigDecimal weekRiseRate,
      BigDecimal riseRate,
      BigDecimal high,
      BigDecimal low,
      BigDecimal monthRiseRate) {
    super();
    this.vol = vol;
    this.last = last;
    this.sell = sell;
    this.buy = buy;
    this.weekRiseRate = weekRiseRate;
    this.riseRate = riseRate;
    this.high = high;
    this.low = low;
    this.monthRiseRate = monthRiseRate;
  }

  @JsonProperty("vol")
  public BigDecimal getVol() {
    return vol;
  }

  @JsonProperty("vol")
  public void setVol(BigDecimal vol) {
    this.vol = vol;
  }

  @JsonProperty("last")
  public BigDecimal getLast() {
    return last;
  }

  @JsonProperty("last")
  public void setLast(BigDecimal last) {
    this.last = last;
  }

  @JsonProperty("sell")
  public BigDecimal getSell() {
    return sell;
  }

  @JsonProperty("sell")
  public void setSell(BigDecimal sell) {
    this.sell = sell;
  }

  @JsonProperty("buy")
  public BigDecimal getBuy() {
    return buy;
  }

  @JsonProperty("buy")
  public void setBuy(BigDecimal buy) {
    this.buy = buy;
  }

  @JsonProperty("weekRiseRate")
  public BigDecimal getWeekRiseRate() {
    return weekRiseRate;
  }

  @JsonProperty("weekRiseRate")
  public void setWeekRiseRate(BigDecimal weekRiseRate) {
    this.weekRiseRate = weekRiseRate;
  }

  @JsonProperty("riseRate")
  public BigDecimal getRiseRate() {
    return riseRate;
  }

  @JsonProperty("riseRate")
  public void setRiseRate(BigDecimal riseRate) {
    this.riseRate = riseRate;
  }

  @JsonProperty("high")
  public BigDecimal getHigh() {
    return high;
  }

  @JsonProperty("high")
  public void setHigh(BigDecimal high) {
    this.high = high;
  }

  @JsonProperty("low")
  public BigDecimal getLow() {
    return low;
  }

  @JsonProperty("low")
  public void setLow(BigDecimal low) {
    this.low = low;
  }

  @JsonProperty("monthRiseRate")
  public BigDecimal getMonthRiseRate() {
    return monthRiseRate;
  }

  @JsonProperty("monthRiseRate")
  public void setMonthRiseRate(BigDecimal monthRiseRate) {
    this.monthRiseRate = monthRiseRate;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("vol", vol)
        .append("last", last)
        .append("sell", sell)
        .append("buy", buy)
        .append("weekRiseRate", weekRiseRate)
        .append("riseRate", riseRate)
        .append("high", high)
        .append("low", low)
        .append("monthRiseRate", monthRiseRate)
        .toString();
  }
}
