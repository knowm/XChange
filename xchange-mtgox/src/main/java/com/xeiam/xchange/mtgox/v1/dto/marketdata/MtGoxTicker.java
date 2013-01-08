/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v1.dto.marketdata;

import org.codehaus.jackson.annotate.JsonProperty;

import com.xeiam.xchange.mtgox.v1.dto.MtGoxValue;

/**
 * Data object representing Ticker from Mt Gox
 * 
 * @immutable
 */
public final class MtGoxTicker {

  private final MtGoxValue high;
  private final MtGoxValue low;
  private final MtGoxValue avg;
  private final MtGoxValue vwap;
  private final MtGoxValue vol;
  private final MtGoxValue last_local;
  private final MtGoxValue last;
  private final MtGoxValue last_orig;
  private final MtGoxValue last_all;
  private final MtGoxValue buy;
  private final MtGoxValue sell;

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
   * @param last_orig
   * @param last_all
   * @param buy
   * @param sell
   */
  public MtGoxTicker(@JsonProperty("high") MtGoxValue high, @JsonProperty("low") MtGoxValue low, @JsonProperty("avg") MtGoxValue avg, @JsonProperty("vwap") MtGoxValue vwap,
      @JsonProperty("vol") MtGoxValue vol, @JsonProperty("last_local") MtGoxValue last_local, @JsonProperty("last") MtGoxValue last, @JsonProperty("last_orig") MtGoxValue last_orig,
      @JsonProperty("last_all") MtGoxValue last_all, @JsonProperty("buy") MtGoxValue buy, @JsonProperty("sell") MtGoxValue sell) {

    this.high = high;
    this.low = low;
    this.avg = avg;
    this.vwap = vwap;
    this.vol = vol;
    this.last_local = last_local;
    this.last = last;
    this.last_orig = last_orig;
    this.last_all = last_all;
    this.buy = buy;
    this.sell = sell;
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

  public MtGoxValue getLast_local() {

    return last_local;
  }

  public MtGoxValue getLast() {

    return last;
  }

  public MtGoxValue getLast_orig() {

    return last_orig;
  }

  public MtGoxValue getLast_all() {

    return last_all;
  }

  public MtGoxValue getBuy() {

    return buy;
  }

  public MtGoxValue getSell() {

    return sell;
  }

  @Override
  public String toString() {

    return "MtGoxTicker [high=" + high + ", low=" + low + ", avg=" + avg + ", vwap=" + vwap + ", vol=" + vol + ", last_local=" + last_local + ", last=" + last + ", last_orig=" + last_orig
        + ", last_all=" + last_all + ", buy=" + buy + ", sell=" + sell + "]";
  }

}
