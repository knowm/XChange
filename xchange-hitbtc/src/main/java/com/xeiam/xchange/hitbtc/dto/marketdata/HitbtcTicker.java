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
package com.xeiam.xchange.hitbtc.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public final class HitbtcTicker {

  private final BigDecimal ask;
  private final BigDecimal bid;
  private final BigDecimal last;
  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal volume;
  private final long timetamp;

  /**
   * Constructor
   * 
   * @param ask
   * @param bid
   * @param last
   * @param low
   * @param high
   * @param volume
   * @param timetamp
   */
  public HitbtcTicker(@JsonProperty("ask") BigDecimal ask, @JsonProperty("bid") BigDecimal bid, @JsonProperty("last") BigDecimal last, @JsonProperty("low") BigDecimal low,
      @JsonProperty("high") BigDecimal high, @JsonProperty("volume") BigDecimal volume, @JsonProperty("timestamp") long timetamp) {

    this.ask = ask;
    this.bid = bid;
    this.last = last;
    this.low = low;
    this.high = high;
    this.volume = volume;
    this.timetamp = timetamp;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public long getTimetamp() {

    return timetamp;
  }

  @Override
  public String toString() {

    return "HitbtcTicker{" + "ask=" + ask + ", bid=" + bid + ", last=" + last + ", low=" + low + ", high=" + high + ", volume=" + volume + ", timetamp=" + timetamp + '}';
  }
}
