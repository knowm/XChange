/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.bitbay.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public final class BitbayTicker {

  private final BigDecimal max;
  private final BigDecimal min;
  private final BigDecimal last;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal vwap;
  private final BigDecimal average;
  private final BigDecimal volume;

  /**
   * Constructor
   * 
   * @param max
   * @param min
   * @param last
   * @param bid
   * @param ask
   * @param vwap
   * @param average
   * @param volume
   */
  public BitbayTicker(@JsonProperty("max") BigDecimal max, @JsonProperty("min") BigDecimal min, @JsonProperty("last") BigDecimal last, @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask, @JsonProperty("vwap") BigDecimal vwap, @JsonProperty("average") BigDecimal average, @JsonProperty("volume") BigDecimal volume) {

    this.max = max;
    this.min = min;
    this.last = last;
    this.bid = bid;
    this.ask = ask;
    this.vwap = vwap;
    this.average = average;
    this.volume = volume;
  }

  public BigDecimal getMax() {

    return max;
  }

  public BigDecimal getMin() {

    return min;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getVwap() {

    return vwap;
  }

  public BigDecimal getAverage() {

    return average;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  @Override
  public String toString() {

    return "BitbayTicker{" + "max=" + max + ", min=" + min + ", last=" + last + ", bid=" + bid + ", ask=" + ask + ", vwap=" + vwap + ", average=" + average + ", volume=" + volume + '}';
  }
}
