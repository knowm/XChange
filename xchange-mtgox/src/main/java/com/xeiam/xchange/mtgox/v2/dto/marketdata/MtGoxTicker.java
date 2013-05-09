/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.mtgox.v1.dto.MtGoxValue;

/**
 * Data object representing Ticker from Mt Gox
 */
public final class MtGoxTicker {

  private final MtGoxValue high;
  private final MtGoxValue low;
  private final MtGoxValue avg;
  private final MtGoxValue vwap;
  private final MtGoxValue vol;
  private final MtGoxValue last;
  private final MtGoxValue buy;
  private final MtGoxValue sell;
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
  public MtGoxTicker(@JsonProperty("high") MtGoxValue high, @JsonProperty("low") MtGoxValue low, @JsonProperty("avg") MtGoxValue avg, @JsonProperty("vwap") MtGoxValue vwap,
      @JsonProperty("vol") MtGoxValue vol, @JsonProperty("last") MtGoxValue last, @JsonProperty("buy") MtGoxValue buy, @JsonProperty("sell") MtGoxValue sell, @JsonProperty("now") long now) {

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

  public MtGoxValue getHigh() {

    return high;
  }

  public MtGoxValue getLow() {

    return low;
  }

  public MtGoxValue getAvg() {

    return avg;
  }

  public MtGoxValue getVwap() {

    return vwap;
  }

  public MtGoxValue getVol() {

    return vol;
  }

  public MtGoxValue getLast() {

    return last;
  }

  public MtGoxValue getBuy() {

    return buy;
  }

  public MtGoxValue getSell() {

    return sell;
  }

  public long getNow() {

    return now;
  }

  @Override
  public String toString() {

    return "MtGoxTicker [high=" + high + ", low=" + low + ", avg=" + avg + ", vwap=" + vwap + ", vol=" + vol + ", last=" + last + ", buy=" + buy + ", sell=" + sell + ", now=" + now + "]";
  }

}
