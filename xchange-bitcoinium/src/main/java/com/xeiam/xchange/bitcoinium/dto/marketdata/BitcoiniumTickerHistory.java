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
 * <p>
 * Data object representing a Ticker History from Bitcoinium Web Service
 * </p>
 */
public final class BitcoiniumTickerHistory {

  /** Price with 3 decimal places of precision */
  private final ArrayList<BigDecimal> priceHistory;

  /** the time stamp (in seconds from epoch) of the oldest data point */
  private final long oldestTimestamp;

  /** the time difference between the previous data point and the current one */
  private final ArrayList<Integer> timestampOffset;

  /**
   * Constructor
   * 
   * @param pp
   * @param t
   * @param tt
   */
  public BitcoiniumTickerHistory(@JsonProperty("pp") ArrayList<BigDecimal> pp, @JsonProperty("t") long t, @JsonProperty("tt") ArrayList<Integer> tt) {

    this.priceHistory = pp;
    this.oldestTimestamp = t;
    this.timestampOffset = tt;
  }

  public ArrayList<BigDecimal> getPriceHistoryList() {

    return this.priceHistory;
  }

  public long getBaseTimestamp() {

    return this.oldestTimestamp;
  }

  public ArrayList<Integer> getTimeStampOffsets() {

    return this.timestampOffset;
  }

  @Override
  public String toString() {

    return "BitcoiniumTickerHistory [priceList=" + priceHistory + ", timestamp=" + oldestTimestamp + ", timeOffsets=" + timestampOffset + "]";
  }

}
