package org.knowm.xchange.paribu.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author semihunaldi */
public final class BTC_TL {

  private final BigDecimal last;
  private final BigDecimal lowestAsk;
  private final BigDecimal highestBid;
  private final BigDecimal percentChange;
  private final BigDecimal volume;
  private final BigDecimal high24hr;
  private final BigDecimal low24hr;

  public BTC_TL(
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("lowestAsk") BigDecimal lowestAsk,
      @JsonProperty("highestBid") BigDecimal highestBid,
      @JsonProperty("percentChange") BigDecimal percentChange,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("high24hr") BigDecimal high24hr,
      @JsonProperty("low24hr") BigDecimal low24hr) {
    this.last = last;
    this.lowestAsk = lowestAsk;
    this.highestBid = highestBid;
    this.percentChange = percentChange;
    this.volume = volume;
    this.high24hr = high24hr;
    this.low24hr = low24hr;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getLowestAsk() {
    return lowestAsk;
  }

  public BigDecimal getHighestBid() {
    return highestBid;
  }

  public BigDecimal getPercentChange() {
    return percentChange;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getHigh24hr() {
    return high24hr;
  }

  public BigDecimal getLow24hr() {
    return low24hr;
  }

  @Override
  public String toString() {
    return "ParibuTicker {"
        + "last="
        + last
        + ", lowestAsk="
        + lowestAsk
        + ", highestBid="
        + highestBid
        + ", percentChange="
        + percentChange
        + ", volume="
        + volume
        + ", high24hr="
        + high24hr
        + ", low24hr="
        + low24hr
        + '}';
  }
}
