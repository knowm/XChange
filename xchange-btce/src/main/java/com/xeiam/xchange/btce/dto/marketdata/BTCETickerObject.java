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
package com.xeiam.xchange.btce.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Ticker from BTCE
 */
public final class BTCETickerObject {

  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal avg;
  private final BigDecimal buy;
  private final BigDecimal sell;
  private final long serverTime;
  private final BigDecimal vol;
  private final BigDecimal volCur;

  /**
   * Constructor
   * 
   * @param high
   * @param low
   * @param vol
   * @param last
   * @param avg
   * @param buy
   * @param serverTime
   * @param volCur
   * @param sell
   */
  public BTCETickerObject(@JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("vol") BigDecimal vol, @JsonProperty("last") BigDecimal last,
      @JsonProperty("avg") BigDecimal avg, @JsonProperty("buy") BigDecimal buy, @JsonProperty("server_time") long serverTime, @JsonProperty("vol_cur") BigDecimal volCur,
      @JsonProperty("sell") BigDecimal sell) {

    this.high = high;
    this.low = low;
    this.last = last;
    this.avg = avg;
    this.buy = buy;
    this.sell = sell;
    this.serverTime = serverTime;
    this.vol = vol;
    this.volCur = volCur;
  }

  public BigDecimal getAvg() {

    return avg;
  }

  public BigDecimal getBuy() {

    return buy;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getSell() {

    return sell;
  }

  public long getServerTime() {

    return serverTime;
  }

  public BigDecimal getVol() {

    return vol;
  }

  public BigDecimal getVolCur() {

    return volCur;
  }

  @Override
  public String toString() {

    return "BTCETickerObject [last=" + last + ", high=" + high + ", low=" + low + ", avg=" + avg + ", buy=" + buy + ", sell=" + sell + ", serverTime=" + serverTime + ", vol=" + vol + ", volCur="
        + volCur + "]";
  }

}
