/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 * Copyright (C) 2012 - 2013 Michael Lagace
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
package com.xeiam.xchange.mtgox.v0.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @deprecated Use V2! This will be removed in 1.8.0+
 */
@Deprecated
public class Ticker {

  private BigDecimal avg;
  private BigDecimal buy;
  private BigDecimal high;
  private BigDecimal last;
  private BigDecimal last_all;
  private BigDecimal last_local;
  private BigDecimal low;
  private BigDecimal sell;
  private BigDecimal vol;
  private BigDecimal vwap;

  /**
   * Constructor
   * 
   * @param high
   * @param low
   * @param avg
   * @param vwap
   * @param vol
   * @param last_local
   * @param last
   * @param last_all
   * @param buy
   * @param sell
   */
  public Ticker(@JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("avg") BigDecimal avg, @JsonProperty("vwap") BigDecimal vwap,
      @JsonProperty("vol") BigDecimal vol, @JsonProperty("last_local") BigDecimal last_local, @JsonProperty("last") BigDecimal last, @JsonProperty("last_all") BigDecimal last_all,
      @JsonProperty("buy") BigDecimal buy, @JsonProperty("sell") BigDecimal sell) {

    this.high = high;
    this.low = low;
    this.avg = avg;
    this.vwap = vwap;
    this.vol = vol;
    this.last_local = last_local;
    this.last = last;
    this.last_all = last_all;
    this.buy = buy;
    this.sell = sell;
  }

  public BigDecimal getAvg() {

    return this.avg;
  }

  public BigDecimal getBuy() {

    return this.buy;
  }

  public BigDecimal getHigh() {

    return this.high;
  }

  public BigDecimal getLast() {

    return this.last;
  }

  public BigDecimal getLast_all() {

    return this.last_all;
  }

  public BigDecimal getLast_local() {

    return this.last_local;
  }

  public BigDecimal getLow() {

    return this.low;
  }

  public BigDecimal getSell() {

    return this.sell;
  }

  public BigDecimal getVol() {

    return this.vol;
  }

  public BigDecimal getVwap() {

    return this.vwap;
  }

  @Override
  public String toString() {

    return "MtGoxTicker [high=" + high + ", low=" + low + ", avg=" + avg + ", vwap=" + vwap + ", vol=" + vol + ", last=" + last + ", buy=" + buy + ", sell=" + sell + "]";
  }
}
