/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.anx.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.anx.v2.dto.ANXValue;

/**
 * Data object representing Ticker from ANX
 */
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
  public ANXTicker(@JsonProperty("high") ANXValue high, @JsonProperty("low") ANXValue low, @JsonProperty("avg") ANXValue avg, @JsonProperty("vwap") ANXValue vwap, @JsonProperty("vol") ANXValue vol,
      @JsonProperty("last") ANXValue last, @JsonProperty("buy") ANXValue buy, @JsonProperty("sell") ANXValue sell, @JsonProperty("now") long now) {

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

    return "ANXTicker [high=" + high + ", low=" + low + ", avg=" + avg + ", vwap=" + vwap + ", vol=" + vol + ", last=" + last + ", buy=" + buy + ", sell=" + sell + ", now=" + now + "]";
  }

}
