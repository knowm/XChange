package org.knowm.xchange.anx.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.anx.v2.dto.ANXValue;

/** Data object representing Ticker from ANX */
public final class ANXTicker {

  private final ANXValue high;
  private final ANXValue low;
  private final ANXValue avg;
  private final ANXValue vwap;
  private final ANXValue vol;
  private final ANXValue last;
  private final ANXValue buy;
  private final ANXValue sell;
  private final long now;

  /**
   * Constructor
   *
   * @param high
   * @param low
   * @param avg
   * @param vwap
   * @param vol
   * @param last
   * @param buy
   * @param sell
   * @param now
   */
  public ANXTicker(
      @JsonProperty("high") ANXValue high,
      @JsonProperty("low") ANXValue low,
      @JsonProperty("avg") ANXValue avg,
      @JsonProperty("vwap") ANXValue vwap,
      @JsonProperty("vol") ANXValue vol,
      @JsonProperty("last") ANXValue last,
      @JsonProperty("buy") ANXValue buy,
      @JsonProperty("sell") ANXValue sell,
      @JsonProperty("now") long now) {

    this.high = high;
    this.low = low;
    this.avg = avg;
    this.vwap = vwap;
    this.vol = vol;
    this.last = last;
    this.buy = buy;
    this.sell = sell;
    this.now = now;
  }

  public ANXValue getHigh() {

    return high;
  }

  public ANXValue getLow() {

    return low;
  }

  public ANXValue getAvg() {

    return avg;
  }

  public ANXValue getVwap() {

    return vwap;
  }

  public ANXValue getVol() {

    return vol;
  }

  public ANXValue getLast() {

    return last;
  }

  public ANXValue getBuy() {

    return buy;
  }

  public ANXValue getSell() {

    return sell;
  }

  public long getNow() {

    return now;
  }

  @Override
  public String toString() {

    return "ANXTicker [high="
        + high
        + ", low="
        + low
        + ", avg="
        + avg
        + ", vwap="
        + vwap
        + ", vol="
        + vol
        + ", last="
        + last
        + ", buy="
        + buy
        + ", sell="
        + sell
        + ", now="
        + now
        + "]";
  }
}
