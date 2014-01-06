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
package com.xeiam.xchange.bitcoinium.dto.marketdata;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Orderbook from Bitcoinium WebService
 */
public final class BitcoiniumOrderbook {

  private final ArrayList<BigDecimal> ap;
  private final ArrayList<BigDecimal> av;
  private final ArrayList<BigDecimal> bp;
  private final ArrayList<BigDecimal> bv;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal last;
  private final BigDecimal volume;
  private final long timestamp;

  public BitcoiniumOrderbook(@JsonProperty("ap") ArrayList<BigDecimal> ap, @JsonProperty("av") ArrayList<BigDecimal> av, @JsonProperty("bp") ArrayList<BigDecimal> bp,
      @JsonProperty("bv") ArrayList<BigDecimal> bv, @JsonProperty("l") BigDecimal last, @JsonProperty("h") BigDecimal high, @JsonProperty("lo") BigDecimal low, @JsonProperty("b") BigDecimal bid,
      @JsonProperty("a") BigDecimal ask, @JsonProperty("v") BigDecimal volume, @JsonProperty("t") long timestamp) {

    this.ap = ap;
    this.av = av;
    this.bp = bp;
    this.bv = bv;
    this.last = last;
    this.volume = volume;
    this.high = high;
    this.low = low;
    this.bid = bid;
    this.ask = ask;
    this.timestamp = timestamp;
  }

  public ArrayList<BigDecimal> getAskPriceList() {

    return this.ap;
  }

  public ArrayList<BigDecimal> getAskVolumeList() {

    return this.av;
  }

  public ArrayList<BigDecimal> getBidPriceList() {

    return this.bp;
  }

  public ArrayList<BigDecimal> getBidVolumeList() {

    return this.bv;
  }

  public BigDecimal getLast() {

    return this.last;
  }

  public long getTimestamp() {

    return this.timestamp;
  }

  public BigDecimal getVolume() {

    return this.volume;
  }

  public BigDecimal getHigh() {

    return this.high;
  }

  public BigDecimal getLow() {

    return this.low;
  }

  public BigDecimal getBid() {

    return this.bid;
  }

  public BigDecimal getAsk() {

    return this.ask;
  }

  @Override
  public String toString() {

    return "BitcoiniumOrderbook [askPrice=" + ap + ", askVolume=" + av + ", buyPrice=" + bp + ", buyVolume=" + bv + ", high=" + high + ", low=" + low + ", bid=" + bid + ", ask=" + ask + ", last="
        + last + ", volume=" + volume + ", timestamp=" + timestamp + "]";
  }

}
