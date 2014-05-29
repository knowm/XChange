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
package com.xeiam.xchange.virtex.v2.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Ticker from VirtEx
 */
public final class VirtExTicker {

  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal sell;
  private final BigDecimal buy;
  private final BigDecimal volume;


  /**
 * @param sell
 * @param buy
 * @param high
 * @param low
 * @param volume
 * @param last
 */
public VirtExTicker(@JsonProperty("sell") BigDecimal sell, @JsonProperty("buy") BigDecimal buy, @JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("volume") BigDecimal volume, @JsonProperty("last") BigDecimal last) {

	this.sell = sell;
	this.buy = buy;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.last = last;
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
  
  public BigDecimal getBuy() {

    return buy;
  }

  public BigDecimal getSell() {

    return sell;
  }

  @Override
  public String toString() {

    return "VirtExTicker [last=" + last + ", high=" + high + ", low=" + low + ", buy=" + buy + ", sell=" + sell +", volume=" + volume + "]";

  }

}
