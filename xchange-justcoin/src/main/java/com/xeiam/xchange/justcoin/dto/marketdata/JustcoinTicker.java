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
package com.xeiam.xchange.justcoin.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Ticker from Justcoin
 * 
 * @author jamespedwards42
 */
public final class JustcoinTicker {

  private final String id;
  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal volume;
  private final int scale;

  /**
   * Constructor
   * 
   * @param id
   * @param high
   * @param low
   * @param volume
   * @param last
   * @param bid
   * @param ask
   * @param scale
   */
  public JustcoinTicker(final @JsonProperty("id") String id, final @JsonProperty("high") BigDecimal high, final @JsonProperty("low") BigDecimal low, final @JsonProperty("volume") BigDecimal volume,
      final @JsonProperty("last") BigDecimal last, final @JsonProperty("bid") BigDecimal bid, final @JsonProperty("ask") BigDecimal ask, final @JsonProperty("scale") int scale) {

    this.id = id;
    this.high = high;
    this.low = low;
    this.last = last;
    this.bid = bid;
    this.ask = ask;
    this.volume = volume;
    this.scale = scale;
  }

  public String getId() {

    return id;
  }

  public BigDecimal getBid() {

    return bid;
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

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public int getScale() {

    return scale;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("JustcoinTicker [id=").append(id).append(", last=").append(last).append(", high=").append(high).append(", low=").append(low).append(", bid=").append(bid).append(", ask=").append(
        ask).append(", volume=").append(volume).append(", scale=").append(scale).append("]");
    return builder.toString();
  }

}
