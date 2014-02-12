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
package com.xeiam.xchange.cexio.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: brox
 * Since: 2/5/14
 */

public class CexIOTicker {

  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final long timestamp;

  /**
   * Constructor
   * 
   * @param last
   * @param high
   * @param low
   * @param volume
   * @param bid
   * @param ask
   */
  public CexIOTicker(@JsonProperty("last") BigDecimal last, @JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("bid") BigDecimal bid, @JsonProperty("ask") BigDecimal ask, @JsonProperty("timestamp") long timestamp) {

    this.last = last;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.bid = bid;
    this.ask = ask;
    this.timestamp = timestamp;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public long getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "CexIOTicker [last=" + last + ", high=" + high + ", low=" + low + ", volume=" + volume + ", bid=" + bid + ", ask=" + ask + ", timestamp=" + timestamp + "]";
  }

}
