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
package com.xeiam.xchange.coinbase.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jamespedwards42
 */
public class CoinbaseHistoricalSpotPrice implements Comparable<CoinbaseHistoricalSpotPrice> {

  private final Date timestamp;
  private final BigDecimal spotRate;

  CoinbaseHistoricalSpotPrice(final Date timestamp, final BigDecimal spotRate) {

    this.timestamp = timestamp;
    this.spotRate = spotRate;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public BigDecimal getSpotRate() {

    return spotRate;
  }

  @Override
  public String toString() {

    return "CoinbaseHistoricalPrice [timestamp=" + timestamp + ", spotRate=" + spotRate + "]";
  }

  @Override
  public int compareTo(CoinbaseHistoricalSpotPrice o) {

    return this.timestamp.compareTo(o.timestamp);
  }

}
