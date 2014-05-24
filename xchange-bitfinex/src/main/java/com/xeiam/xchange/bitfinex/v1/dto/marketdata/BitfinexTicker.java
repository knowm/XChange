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
package com.xeiam.xchange.bitfinex.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexTicker {

  private final BigDecimal mid;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal last;
  private final BigDecimal volume;
  private final float timestamp;

/**
 * @param mid
 * @param bid
 * @param ask
 * @param low
 * @param high
 * @param last
 * @param timestamp
 * @param volume
 */
public BitfinexTicker(@JsonProperty("mid") BigDecimal mid, 
		  @JsonProperty("bid") BigDecimal bid, 
		  @JsonProperty("ask") BigDecimal ask, 
		  @JsonProperty("low") BigDecimal low, 
		  @JsonProperty("high") BigDecimal high, 
		  @JsonProperty("last_price") BigDecimal last,
	      @JsonProperty("timestamp") float timestamp,
	      @JsonProperty("volume") BigDecimal volume) {

    this.mid = mid;
    this.bid = bid;
    this.ask = ask;
    this.last = last;
    this.volume = volume;
    this.high = high;
    this.low = low;
    this.timestamp = timestamp;
  }

  public BigDecimal getMid() {

    return mid;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }
  
  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLast_price() {

    return last;
  }
  
  public BigDecimal getVolume() {

    return volume;
  }

  public float getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "BitfinexTicker [mid=" + mid + ", bid=" + bid + ", ask=" + ask + ", low=" + low + ", high=" + high + ", last=" + last + ", timestamp=" + timestamp + "]";
  }

}
